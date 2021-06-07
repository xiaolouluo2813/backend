package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.TxTypeConstants;
import com.blockchain.server.eos.dto.WalletDTO;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.entity.Wallet;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.feign.UserFeign;
import com.blockchain.server.eos.mapper.WalletMapper;
import com.blockchain.server.eos.service.IEosClearingTotalService;
import com.blockchain.server.eos.service.IEosTokenService;
import com.blockchain.server.eos.service.IEosWalletService;
import com.blockchain.server.eos.service.IEosWalletTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EosWalletServiceImpl implements IEosWalletService {

    @Autowired
    UserFeign userFeign;
    @Autowired
    WalletMapper eosWalletMapper;
    @Autowired
    IEosWalletTransferService eosWalletTransferService;
    @Autowired
    IEosTokenService eosTokenService;
    @Autowired
    IEosClearingTotalService eosClearingTotalService;

    @Override
    public List<Wallet> selectByUserId(String userId) {
        ExceptionPreconditionUtils.checkNotNull(userId, new EosWalletException(EosWalletEnums.NULL_USEROPENID));
        Wallet where = new Wallet();
        where.setUserOpenId(userId);
        List<Wallet> wallets = eosWalletMapper.select(where);
        if (wallets.size() <= 0) {
            throw new EosWalletException(EosWalletEnums.NULL_TOKENADDR);
        }
        return wallets;
    }

    @Override
    public WalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenAddr, String walletType) {
        return eosWalletMapper.selectByUserOpenIdAndTokenNameAndWalletType(userId, tokenAddr, walletType);
    }

    @Override
    public Wallet findByAddrAndCoinName(int id, String coinName) {
        Wallet where = new Wallet();
        where.setId(id);
        where.setTokenSymbol(coinName);
        Wallet wallet = eosWalletMapper.selectOne(where);
        if (wallet == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        return wallet;
    }

    @Override
    @Transactional
    public void updateFreeBalance(String userId, String tokenAddr, String walletType, BigDecimal amount, Date date) {
        WalletDTO wallet = this.findByUserIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
        int row = eosWalletMapper.updateBalanceByUserIdInRowLock(userId, tokenAddr, walletType, amount, amount,
                BigDecimal.ZERO, date);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
    }

    @Override
    public List<WalletBaseDTO> select(WalletParamsDTO paramsDTO) {
        if (paramsDTO != null && paramsDTO.getUserName() != null && !paramsDTO.getUserName().equals("")) {
            UserBaseInfoDTO userBaseInfoDTO = ConditionsUserId(paramsDTO.getUserName());
            //用户存在，添加条件
            if (userBaseInfoDTO != null) {
                paramsDTO.setUserId(userBaseInfoDTO.getUserId());
            } else {
                //用户不存在，返回空
                return null;
            }
        }
        List<WalletBaseDTO> wallets = eosWalletMapper.selectQuery(paramsDTO);
        return fillUserInfos(wallets);
    }

    /**
     * 冻结与解冻方法
     *
     * @param walletOrderDTO
     * @return
     */
    @Override
    @Transactional
    public Integer updateWalletOrder(WalletOrderDTO walletOrderDTO) {
        ExceptionPreconditionUtils.notEmpty(walletOrderDTO);
        BigDecimal freeBalance = walletOrderDTO.getFreeBalance();
        BigDecimal freezeBalance = walletOrderDTO.getFreezeBalance();
        ExceptionPreconditionUtils.notEmpty(freeBalance);
        ExceptionPreconditionUtils.notEmpty(freezeBalance);
        // 判断传过来的可用余额和冻结余额是否有误
        if (freeBalance.add(freezeBalance).compareTo(BigDecimal.ZERO) != 0)
            throw new EosWalletException(EosWalletEnums.DATA_EXCEPTION_ERROR);
        Token token = eosTokenService.findByTokenSymbol(walletOrderDTO.getTokenName());
        // 判断是否存在该钱包
        WalletDTO walletDTO = this.findByUserIdAndTokenAddrAndWalletType(walletOrderDTO.getUserId(),
                token.getTokenName(), walletOrderDTO.getWalletType());
        // 对比可用余额和冻结余额
        if (freeBalance.add(walletDTO.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
        if (freezeBalance.add(walletDTO.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        }
        return eosWalletMapper.updateBalanceByUserIdInRowLock(walletOrderDTO.getUserId(), token.getTokenName(),
                walletOrderDTO.getWalletType(), walletOrderDTO.getFreeBalance().add(walletOrderDTO.getFreezeBalance()),
                walletOrderDTO.getFreeBalance(), walletOrderDTO.getFreezeBalance(), new Date());
    }

    @Override
    @Transactional
    public void updateBlance(String addr, String coinName, String freeBlance, String freezeBlance, String txType) {
        Date date = new Date();
        // 验证余额数据格式
        BigDecimal _freeBlance = BigDecimal.ZERO;
        BigDecimal _freezeBlance = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        try {
            _freeBlance = new BigDecimal(freeBlance);
            _freezeBlance = new BigDecimal(freezeBlance);
            total = _freeBlance.add(_freezeBlance);
        } catch (Exception e) {
            throw new EosWalletException(EosWalletEnums.DATA_EXCEPTION_ERROR);
        }
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            throw new EosWalletException(EosWalletEnums.DATA_EXCEPTION_ERROR);
        }
        // 获取用户钱包
        Wallet wallet = this.findByAddrAndCoinName(Integer.valueOf(addr), coinName);
        int row = eosWalletMapper.updateBalanceByUserIdInRowLock(wallet.getUserOpenId(), wallet.getTokenName(), wallet.getWalletType(),
                total, _freeBlance, _freezeBlance, date);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
//        String userId = null == SecurityUtils.getUser() ? WalletConstant.BACKEND : SecurityUtils.getUser().getId();
        WalletTransfer tx = new WalletTransfer();
        tx.setId(UUID.randomUUID().toString());
        tx.setStatus(TxTypeConstants.SUCCESS);
        tx.setAmount(total.abs());
        tx.setTokenName(wallet.getTokenName());
        tx.setTokenSymbol(wallet.getTokenSymbol());
        tx.setTransferType(txType);
        tx.setTimestamp(date);
        int insertRow = 0;
        if (total.compareTo(BigDecimal.ZERO) >= 0) {
            tx.setToId(wallet.getId());
            insertRow = eosWalletTransferService.insertWalletTransfer(tx);
        } else {
            tx.setFromId(wallet.getId());
            insertRow = eosWalletTransferService.insertWalletTransfer(tx);
        }
        if (insertRow == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    @Transactional
    public void updateBlance(String addr, String coinName, BigDecimal freeBlance, BigDecimal freezeBlance, Date date) {
        // 获取用户钱包
        Wallet wallet = this.findByAddrAndCoinName(Integer.valueOf(addr), coinName);
        int row = eosWalletMapper.updateBalanceByUserIdInRowLock(wallet.getUserOpenId(), wallet.getTokenName(), wallet.getWalletType(),
                freeBlance.add(freezeBlance), freeBlance, freezeBlance, date);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public boolean isId(String walletId) {
        Wallet wallet = eosWalletMapper.selectByPrimaryKey(walletId);
        return null != wallet;
    }

    /**
     * 钱包余额变动方法
     *
     * @param walletChangeDTO
     * @return
     */
    @Override
    @Transactional
    public Integer handleWalletChange(WalletChangeDTO walletChangeDTO) {
        // 判断参数是否为空
        ExceptionPreconditionUtils.notEmpty(walletChangeDTO);
        BigDecimal freeBalance = walletChangeDTO.getFreeBalance();
        BigDecimal freezeBalance = walletChangeDTO.getFreezeBalance();
        BigDecimal gasBalance = walletChangeDTO.getGasBalance();
        ExceptionPreconditionUtils.notEmpty(freeBalance);
        ExceptionPreconditionUtils.notEmpty(freezeBalance);
        ExceptionPreconditionUtils.notEmpty(gasBalance);
        // 判断是否存在该钱包
        Token token = eosTokenService.findByTokenSymbol(walletChangeDTO.getTokenName());
        WalletDTO walletDTO = this.findByUserIdAndTokenAddrAndWalletType(walletChangeDTO.getUserId(),
                token.getTokenName(), walletChangeDTO.getWalletType());
        BigDecimal dtoBalance = walletDTO.getBalance();
        BigDecimal dtoFreeBalance = walletDTO.getFreeBalance();
        BigDecimal dtoFreezeBalance = walletDTO.getFreezeBalance();
        // 判断余额是否充足
        if (dtoFreezeBalance.add(freezeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENTZE_ERROR);
        if (dtoFreeBalance.add(freeBalance).compareTo(BigDecimal.ZERO) < 0)
            throw new EosWalletException(EosWalletEnums.NUMBER_INSUFFICIENT_ERROR);
//        if (dtoBalance.compareTo(dtoFreeBalance.add(dtoFreezeBalance).add(freeBalance.add(freezeBalance))) > 0)
//            throw new EosWalletException(EosWalletEnums.DATA_EXCEPTION_ERROR);
        //记录总额改动
        BigDecimal balance = BigDecimal.ZERO;
        Date now = new Date();
        // 添加记录
        WalletTransfer walletTransfer = new WalletTransfer();
        walletTransfer.setId(UUID.randomUUID().toString());
        walletTransfer.setHash(walletChangeDTO.getRecordId());
        balance = freeBalance.add(freezeBalance);
        // 可用余额增加
        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            walletTransfer.setToId(walletDTO.getId());
        } else {
            walletTransfer.setFromId(walletDTO.getId());
        }

        walletTransfer.setAmount(balance);
        walletTransfer.setTokenName(token.getTokenName());
        walletTransfer.setTokenSymbol(walletDTO.getTokenSymbol());
        walletTransfer.setGasPrice(gasBalance);
        walletTransfer.setGasTokenName(walletDTO.getTokenSymbol());
        walletTransfer.setGasTokenSymbol(walletDTO.getTokenSymbol());
        walletTransfer.setGasTokenType(walletDTO.getTokenName());

        walletTransfer.setTransferType(walletDTO.getWalletType());
        walletTransfer.setStatus(TxTypeConstants.SUCCESS);
        walletTransfer.setTimestamp(now);
        eosWalletTransferService.insertWalletTransfer(walletTransfer);
        return eosWalletMapper.updateBalanceByUserIdInRowLock(walletChangeDTO.getUserId(), token.getTokenName(),
                walletChangeDTO.getWalletType(), walletChangeDTO.getFreeBalance().add(walletChangeDTO.getFreezeBalance()),
                walletChangeDTO.getFreeBalance(), walletChangeDTO.getFreezeBalance(), now);
    }

    /**
     * @Description: 获取用户信息
     * @Param: [list]
     * @return: java.util.List<com.blockchain.common.base.dto.wallet.WalletBaseDTO>
     * @Author: Liu.sd
     * @Date: 2019/3/23
     */
    private List<WalletBaseDTO> fillUserInfos(List<WalletBaseDTO> list) {
        Set<String> userIds = new HashSet<>();
        for (WalletBaseDTO row : list) {
            userIds.add(row.getUserId());
        }
        //  获取用户信息
        ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
        if (result == null) throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        Map<String, UserBaseInfoDTO> userMap = result.getData();
        for (WalletBaseDTO row : list) {
            String userId = row.getUserId();
            if (userMap.containsKey(userId)) row.setUserBaseInfoDTO(userMap.get(userId));
        }
        return list;
    }

    /**
     * @Description: 根据手机号获取用户信息
     * @Param: [userName]
     * @return: com.blockchain.common.base.dto.user.UserBaseInfoDTO
     * @Author: Liu.sd
     * @Date: 2019/3/23
     */
    private UserBaseInfoDTO ConditionsUserId(String userName) {
        //  获取用户信息
        ResultDTO<UserBaseInfoDTO> result = userFeign.selectUserInfoByUserName(userName);
        if (result == null) throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        return result.getData();
    }

}
