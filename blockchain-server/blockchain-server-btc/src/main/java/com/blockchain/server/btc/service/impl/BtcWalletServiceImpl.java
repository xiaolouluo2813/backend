package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.dto.wallet.WalletBaseDTO;
import com.blockchain.common.base.dto.wallet.WalletParamsDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.btc.common.constants.OutTxConstants;
import com.blockchain.server.btc.common.constants.TxTypeConstants;
import com.blockchain.server.btc.common.constants.UsdtConstans;
import com.blockchain.server.btc.common.constants.WalletConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.entity.BtcCollectionTransfer;
import com.blockchain.server.btc.entity.BtcWallet;
import com.blockchain.server.btc.entity.BtcWalletTransfer;
import com.blockchain.server.btc.feign.UserFeign;
import com.blockchain.server.btc.mapper.BtcWalletMapper;
import com.blockchain.server.btc.rpc.BtcUtils;
import com.blockchain.server.btc.rpc.UsdtUtils;
import com.blockchain.server.btc.service.*;
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
public class BtcWalletServiceImpl implements IBtcWalletService {

    @Autowired
    UserFeign userFeign;
    @Autowired
    BtcWalletMapper btcWalletMapper;
    @Autowired
    IBtcWalletTransferService btcWalletTransferService;
    @Autowired
    IBtcClearingTotalService btcClearingTotalService;
    @Autowired
    IConfigWalletParamService configWalletParamService;
    @Autowired
    IBtcCollectionTransferService collectionTransferService;

    @Autowired
    UsdtUtils usdtUtils;
    @Autowired
    BtcUtils btcUtils;

    @Override
    public List<BtcWallet> selectByUserId(String userId) {
        ExceptionPreconditionUtils.checkNotNull(userId, new BtcWalletException(BtcWalletEnums.NULL_USEROPENID));
        BtcWallet where = new BtcWallet();
        where.setUserOpenId(userId);
        List<BtcWallet> wallets = btcWalletMapper.select(where);
        if (wallets.size() <= 0) {
            throw new BtcWalletException(BtcWalletEnums.NULL_TOKENADDR);
        }
        return wallets;
    }

    @Override
    public List<WalletBaseDTO> selectBlock(WalletParamsDTO paramsDTO) {
        List<WalletBaseDTO> list = this.select(paramsDTO);
        for (WalletBaseDTO row : list) {
            row.setFreeBalance(null);
            row.setFreezeBalance(null);
            BigDecimal amount = BigDecimal.ZERO;
            try {
                if (paramsDTO.getCoinName().equalsIgnoreCase(WalletConstants.BTC)) {
                    amount = btcUtils.getBalanceByAddr(row.getAddr());
                } else {
                    amount = usdtUtils.getBalance(row.getAddr());
                }
            } catch (Exception e) {
            }
            row.setBalance(amount.compareTo(BigDecimal.ZERO) == 0 ? "0" : amount.toString());
        }
        return list;
    }

    @Override
    public BtcWallet findByAddrAndCoinName(String addr, String coinName) {
        BtcWallet wallet = new BtcWallet();
        wallet.setAddr(addr);
        wallet.setTokenSymbol(coinName);
        BtcWallet wallet1 = btcWalletMapper.selectOne(wallet);
        if (wallet == null) {
            throw new BtcWalletException(BtcWalletEnums.NULL_TOKENADDR);
        }
        return wallet1;
    }

    @Override
    public BtcWalletDTO findByUserIdAndTokenAddrAndWalletType(String userId, String tokenAddr, String walletType) {
        return btcWalletMapper.selectByUserOpenIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
    }

    @Override
    @Transactional
    public void updateFreeBalance(String userId, String tokenAddr, String walletType, BigDecimal amount, Date date) {
        BtcWalletDTO wallet = this.findByUserIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
        int row = btcWalletMapper.updateBalanceByUserIdInRowLock(userId, tokenAddr, walletType, amount, amount,
                BigDecimal.ZERO, date);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateBalance(String userId, String tokenAddr, String walletType, BigDecimal freeBalance, BigDecimal freezeBalance, Date date) {
        BtcWalletDTO wallet = this.findByUserIdAndTokenAddrAndWalletType(userId, tokenAddr, walletType);
        int row = btcWalletMapper.updateBalanceByUserIdInRowLock(userId, tokenAddr, walletType, freeBalance.add(freezeBalance), freeBalance, freezeBalance, date);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.NUMBER_INSUFFICIENT_ERROR);
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
        List<WalletBaseDTO> wallets = btcWalletMapper.selectQuery(paramsDTO);
        return fillUserInfos(wallets);
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
            throw new BtcWalletException(BtcWalletEnums.DATA_EXCEPTION_ERROR);
        }
        if (total.compareTo(BigDecimal.ZERO) == 0) {
            throw new BtcWalletException(BtcWalletEnums.DATA_EXCEPTION_ERROR);
        }
        // 获取用户钱包
        BtcWallet wallet = this.findByAddrAndCoinName(addr, coinName);
        this.updateBalance(wallet.getUserOpenId(), wallet.getTokenId().toString(), wallet.getWalletType(), _freeBlance, _freezeBlance, date);

        // 插入加币减币的记录
        String userId = null == SecurityUtils.getUser() ? WalletConstant.BACKEND : SecurityUtils.getUser().getId();
        BtcWalletTransfer tx = new BtcWalletTransfer();
        tx.setId(UUID.randomUUID().toString());
        tx.setStatus(TxTypeConstants.SUCCESS);
        tx.setAmount(total.abs().doubleValue());
        tx.setTokenId(wallet.getTokenId());
        tx.setTokenSymbol(wallet.getTokenSymbol());
        tx.setTransferType(txType);
        tx.setUpdateTime(date);
        tx.setCreateTime(date);
        int insertRow = 0;
        if (total.compareTo(BigDecimal.ZERO) >= 0) {
            tx.setToAddr(wallet.getAddr());
            tx.setFromAddr(userId);
            insertRow = btcWalletTransferService.insertTransfer(tx);
        } else {
            tx.setToAddr(userId);
            tx.setFromAddr(wallet.getAddr());
            insertRow = btcWalletTransferService.insertTransfer(tx);
        }
        if (insertRow == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public Map<String, String> totalBtc() {
        Map<String, String> map = new HashMap<>();
        map.put("BTC", "0");
        try {
            BigDecimal amount = btcUtils.getBalance(null);
            map.put("BTC", amount.toPlainString());
        } catch (Exception e) {
        }
        return map;
    }

    @Override
    public Map<String, String> getGasWallet() {
        Map<String, String> map = new HashMap<>();
        String addr = configWalletParamService.getBtcGas();
        try {
            BigDecimal amount = btcUtils.getBalanceByAddr(addr);
            map.put("addr", addr);
            map.put("amount", amount.compareTo(BigDecimal.ZERO) == 0 ? "0" : amount.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void rpcTx(String fromAddr, String toAddr) {
        ExceptionPreconditionUtils.checkStringNotBlank(toAddr, new BtcWalletException(BtcWalletEnums.NULL_ADDR));
        try {
            btcUtils.validateAddress(fromAddr);
            btcUtils.validateAddress(toAddr);
            // 获取USDT
            BigDecimal tokenAmout = usdtUtils.getBalance(fromAddr);
            // 获取手续费账号
            String gasAddr = configWalletParamService.getBtcGas();
            // 产生一笔BTC 代币转账
            String hash = usdtUtils.fundedSendAll(fromAddr, toAddr, UsdtConstans.NET, gasAddr);
        } catch (BtcWalletException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public String getBtc(String addr) {
        BigDecimal balance = BigDecimal.ZERO;
        try {
            balance = btcUtils.getBalanceByAddr(addr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return balance.compareTo(BigDecimal.ZERO) == 0 ? "0" : balance.stripTrailingZeros().toPlainString();
    }

    @Override
    public Set<String> getAllWalletAddr() {
        return btcWalletMapper.getAllWalletAddr();
    }

    @Override
    public void goBtcTx(String toAddr) {
        Date endDate = new Date();
        try {
            btcUtils.validateAddress(toAddr);
            BigDecimal btcAmount = btcUtils.getBalance(null);
            btcAmount = btcAmount.subtract(new BigDecimal("0.00005"));
            String hash = btcUtils.sendtoaddress(toAddr, btcAmount.doubleValue());
            BtcCollectionTransfer tx = new BtcCollectionTransfer();
            SessionUserDTO userDTO = SecurityUtils.getUser();
            tx.setCreateTime(endDate);
            tx.setUpdateTime(endDate);
            tx.setFromAddr("ALL");
            tx.setToAddr(toAddr);
            tx.setTokenAddr(WalletConstants.BTC);
            tx.setUserId(userDTO == null ? "BAM" : userDTO.getId());
            tx.setTokenSymbol(WalletConstants.BTC);
            tx.setHash(hash);
            tx.setId(UUID.randomUUID().toString());
            tx.setStatus(OutTxConstants.PACK);
            tx.setAmount(btcAmount);
            collectionTransferService.insert(tx);
        } catch (BtcWalletException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (result == null) throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
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
     * @Param: [mobilePhone]
     * @return: com.blockchain.common.base.dto.user.UserBaseInfoDTO
     * @Author: Liu.sd
     * @Date: 2019/3/23
     */
    private UserBaseInfoDTO ConditionsUserId(String userName) {
        //  获取用户信息
        ResultDTO<UserBaseInfoDTO> result = userFeign.selectUserInfoByUserName(userName);
        if (result == null) throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        return result.getData();
    }


}
