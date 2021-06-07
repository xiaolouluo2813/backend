package com.blockchain.server.btc.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.btc.common.constants.OutTxConstants;
import com.blockchain.server.btc.common.constants.TxTypeConstants;
import com.blockchain.server.btc.common.constants.WalletConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.dto.BtcWalletTransferDTO;
import com.blockchain.server.btc.dto.BtcWalletTxBillDTO;
import com.blockchain.server.btc.entity.BtcToken;
import com.blockchain.server.btc.entity.BtcWallet;
import com.blockchain.server.btc.entity.BtcWalletTransfer;
import com.blockchain.server.btc.feign.UserFeign;
import com.blockchain.server.btc.mapper.BtcWalletTransferMapper;
import com.blockchain.server.btc.service.IBtcTokenService;
import com.blockchain.server.btc.service.IBtcWalletService;
import com.blockchain.server.btc.service.IBtcWalletTransferService;
import com.blockchain.server.btc.service.WalletOutService;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BtcWalletTransferServiceImpl implements IBtcWalletTransferService, ITxTransaction {

    @Autowired
    private BtcWalletTransferMapper transferMapper;
    @Autowired
    private IBtcWalletService btcWalletService;
    @Autowired
    private IBtcTokenService btcTokenService;
    @Autowired
    private WalletOutService walletOutService;
    @Autowired
    private UserFeign userFeign;


    @Override
    public Integer insertTransfer(BtcWalletTransfer btcWalletTransfer) {
        return transferMapper.insertSelective(btcWalletTransfer);
    }

    @Override
    public BtcWalletTransfer findById(String txId) {
        ExceptionPreconditionUtils.checkStringNotBlank(txId, new BtcWalletException(BtcWalletEnums.NULL_TXID));
        BtcWalletTransfer transfer = transferMapper.selectByPrimaryKey(txId);
        ExceptionPreconditionUtils.checkNotNull(transfer, new BtcWalletException(BtcWalletEnums.NULL_TX));
        return transfer;
    }

    @Override
    public WalletTxDTO findOutTransfer(String txId) {
        WalletTxDTO walletTxDTO = transferMapper.findOutTx(txId);
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserId(walletTxDTO.getUserId());
        walletTxDTO.setUserBaseInfoDTO(userBaseInfoDTO);
        return fillUserInfo(walletTxDTO);
    }

    @Override
    public WalletTxDTO findInTransfer(String txId) {
        WalletTxDTO walletTxDTO = transferMapper.findInTx(txId);
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserId(walletTxDTO.getUserId());
        walletTxDTO.setUserBaseInfoDTO(userBaseInfoDTO);
        return fillUserInfo(walletTxDTO);
    }

    @Override
    public List<WalletTxDTO> selectOutTransfer(WalletTxParamsDTO params) {
        //如果查询条件存在用户名，则调用feign查询用户id
        if (StringUtils.isNotBlank(params.getUserName())) {
            UserBaseInfoDTO user = selectUserByUserName(params.getUserName());
            if (user == null) {
                return null;
            }
            params.setUserId(user.getUserId());
        }
        params.setTxType(TxTypeConstants.OUT);
        List<WalletTxDTO> list = transferMapper.selectOutTx(params);
        if (list.size() == 0) return list;
        return fillUserInfos(list);
    }

    @Override
    public List<WalletTxDTO> selectInTransfer(WalletTxParamsDTO params) {
        //如果查询条件存在用户名，则调用feign查询用户id
        if (StringUtils.isNotBlank(params.getUserName())) {
            UserBaseInfoDTO user = selectUserByUserName(params.getUserName());
            if (user == null) {
                return null;
            }
            params.setUserId(user.getUserId());
        }
        params.setTxType(TxTypeConstants.IN);
        List<WalletTxDTO> list = transferMapper.selectInTx(params);
        if (list.size() == 0) return list;
        return fillUserInfos(list);
    }

    @Override
    public BtcWalletTxBillDTO selectByAddrAndTime(String addr, String tokenAddr, Date startDate, Date endDate) {
        // 查询该区间的流水记录
        List<BtcWalletTransferDTO> txs = transferMapper.selectByAddrAndTime(addr, tokenAddr, startDate, endDate);
        // 定义数据MAP，整合数据
        // 第一层以{币种地址}为KEY，数据详情为Value
        // 第二层以{记录类型}为KEY,总和为Value
        Map<String, Map<String, BigDecimal>> formMap = new HashMap<>();
        Map<String, Map<String, BigDecimal>> toMap = new HashMap<>();
        BigDecimal fromAmount = BigDecimal.ZERO;
        BigDecimal toAmount = BigDecimal.ZERO;
        for (BtcWalletTransferDTO row : txs) {
            if (addr.equalsIgnoreCase(row.getFromAddr())) {
                fromAmount = fromAmount.add(this.fillData(formMap, row));
            }
            if (addr.equalsIgnoreCase(row.getToAddr())) {
                toAmount = toAmount.add(this.fillData(toMap, row));
            }
        }
        BtcWalletTxBillDTO ethWalletTxBillDTO = new BtcWalletTxBillDTO();
        ethWalletTxBillDTO.setFromMap(formMap);
        ethWalletTxBillDTO.setToMap(toMap);
        ethWalletTxBillDTO.setCountFromAmount(fromAmount);
        ethWalletTxBillDTO.setCountToAmount(toAmount);
        ethWalletTxBillDTO.setCountAmount(toAmount.subtract(fromAmount));
        return ethWalletTxBillDTO;
    }

    @Override
    @Transactional
    public BtcWalletTransfer updateStatus(String id, int status) {
        BtcWalletTransfer eosWalletTransfer = this.findById(id);
        eosWalletTransfer.setStatus(status);
        int row = transferMapper.updateByPrimaryKeySelective(eosWalletTransfer);
        if (row == 0) throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        return eosWalletTransfer;
    }

    @Override
    @Transactional
    public void handleOut(String id, int status) {
        ExceptionPreconditionUtils.checkStringNotBlank(id, new BtcWalletException(BtcWalletEnums.NULL_TXID));
        BtcWalletTransfer tx = transferMapper.findByIdForUpdate(id);
        ExceptionPreconditionUtils.checkNotNull(tx, new BtcWalletException(BtcWalletEnums.NULL_TX));
        if(tx.getStatus() != OutTxConstants.RECHECK){
            return;
        }
        String hash = walletOutService.blockTransfer(tx);
        if (hash == null){
            return;
        }
        tx.setStatus(status);
        tx.setHash(hash);
        tx.setUpdateTime(new Date());
        int row = transferMapper.updateByPrimaryKeySelective(tx);
        if (row == 0) throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
    }

    @Override
    @Transactional
    @TxTransaction
    public BtcWalletDTO handleOrder(WalletOrderDTO walletOrderDTO) {
        BtcToken btcToken = btcTokenService.findByTokenName(walletOrderDTO.getTokenName());
        //查询用户钱包
        BtcWalletDTO btcWalletDTO = btcWalletService.findByUserIdAndTokenAddrAndWalletType(walletOrderDTO.getUserId(), btcToken.getTokenId().toString(), walletOrderDTO.getWalletType());

        if (btcWalletDTO.getFreeBalance().add(walletOrderDTO.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0
                || btcWalletDTO.getFreezeBalance().add(walletOrderDTO.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new BtcWalletException(BtcWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }

        //加减钱包可用余额、冻结余额，之间转换
        btcWalletService.updateBalance(btcWalletDTO.getUserOpenId(), btcWalletDTO.getTokenId().toString(),
                btcWalletDTO.getWalletType(), walletOrderDTO.getFreeBalance(), walletOrderDTO.getFreezeBalance(), new Date());

        //返回加减余额后的数据
        return btcWalletService.findByUserIdAndTokenAddrAndWalletType(walletOrderDTO.getUserId(), btcToken.getTokenId().toString(), walletOrderDTO.getWalletType());
    }

    @Override
    @Transactional
    @TxTransaction
    public Integer handleChange(WalletChangeDTO walletChangeDTO) {
        double freeBalance = walletChangeDTO.getFreeBalance().doubleValue();
        double freezeBalance = walletChangeDTO.getFreezeBalance().doubleValue();
        double totalBalance = freeBalance + freezeBalance;

        Date now = new Date();

        BtcToken btcToken = btcTokenService.findByTokenName(walletChangeDTO.getTokenName());
        //查询用户钱包
        BtcWalletDTO btcWalletDTO = btcWalletService.findByUserIdAndTokenAddrAndWalletType(walletChangeDTO.getUserId(), btcToken.getTokenId().toString(), walletChangeDTO.getWalletType());

        if (btcWalletDTO.getFreeBalance().add(walletChangeDTO.getFreeBalance()).compareTo(BigDecimal.ZERO) < 0
                || btcWalletDTO.getFreezeBalance().add(walletChangeDTO.getFreezeBalance()).compareTo(BigDecimal.ZERO) < 0) {
            throw new BtcWalletException(BtcWalletEnums.NUMBER_INSUFFICIENT_ERROR);
        }

        if (freeBalance != 0 || freezeBalance != 0) {
            //加减钱包可用余额、冻结余额、总额
            btcWalletService.updateBalance(btcWalletDTO.getUserOpenId(), btcWalletDTO.getTokenId().toString(),
                    btcWalletDTO.getWalletType(), walletChangeDTO.getFreeBalance(), walletChangeDTO.getFreezeBalance(), new Date());
        }

        //插入一条交易记录
        BtcWalletTransfer btcWalletTransfer = new BtcWalletTransfer();
        if (totalBalance < 0) {
            btcWalletTransfer.setFromAddr(btcWalletDTO.getAddr());
            btcWalletTransfer.setToAddr(null);
        } else {
            btcWalletTransfer.setFromAddr(null);
            btcWalletTransfer.setToAddr(btcWalletDTO.getAddr());
        }
        btcWalletTransfer.setId(UUID.randomUUID().toString());
        btcWalletTransfer.setHash(walletChangeDTO.getRecordId());

        btcWalletTransfer.setAmount(Math.abs(totalBalance));
        btcWalletTransfer.setTokenId(btcToken.getTokenId());
        btcWalletTransfer.setTokenSymbol(walletChangeDTO.getTokenName());
        btcWalletTransfer.setGasPrice(walletChangeDTO.getGasBalance().doubleValue());
        btcWalletTransfer.setTransferType(walletChangeDTO.getWalletType());
        btcWalletTransfer.setStatus(TxTypeConstants.SUCCESS);
        btcWalletTransfer.setCreateTime(now);
        btcWalletTransfer.setUpdateTime(now);
        int countIt = insertTransfer(btcWalletTransfer);

        if (countIt != 1) {
            throw new BtcWalletException(BtcWalletEnums.TRANSFER_ERROR);
        }

        return 1;
    }

    @Override
    @Transactional
    public void handPackError(String id) {
        // 修改记录为失败状态
        BtcWalletTransfer tx = this.updateStatus(id, OutTxConstants.ERROR);
        //打包失败，冻结余额回退回可用余额
        BtcWallet wallet = btcWalletService.findByAddrAndCoinName(tx.getFromAddr(), tx.getTokenSymbol());
        btcWalletService.updateBalance(wallet.getUserOpenId(), wallet.getTokenId().toString(), wallet.getWalletType(),
                new BigDecimal(tx.getAmount()), new BigDecimal(tx.getAmount()).negate(), tx.getUpdateTime());

    }

    @Override
    @Transactional
    public void handPackSuccess(String id) {
        // 修改记录为失败状态
        BtcWalletTransfer tx = this.updateStatus(id, OutTxConstants.SUCCESS);
        // 打包成功，扣除冻结金额
        BtcWallet wallet = btcWalletService.findByAddrAndCoinName(tx.getFromAddr(), tx.getTokenSymbol());
        btcWalletService.updateBalance(wallet.getUserOpenId(), wallet.getTokenId().toString(), wallet.getWalletType(),
                BigDecimal.ZERO, new BigDecimal(tx.getAmount()).negate(), tx.getUpdateTime());
    }

    @Override
    public void handReject(String id) {
        // 修改记录为失败状态
        BtcWalletTransfer tx = this.updateStatus(id, OutTxConstants.REJECT);
        //驳回，解除冻结金额
        BtcWallet wallet = btcWalletService.findByAddrAndCoinName(tx.getFromAddr(), tx.getTokenSymbol());
        btcWalletService.updateBalance(wallet.getUserOpenId(), wallet.getTokenId().toString(), wallet.getWalletType(),
                new BigDecimal(tx.getAmount()), new BigDecimal(tx.getAmount()).negate(), tx.getUpdateTime());
    }

    /**
     * 数据整合
     *
     * @param dataMap 数据集合
     * @param row     行
     */
    private BigDecimal fillData(Map<String, Map<String, BigDecimal>> dataMap, BtcWalletTransferDTO row) {
        //1、数据取值加载，累加值
        String tokenAddr = row.getTokenId().toString(); // 币种标识
        String txType = row.getTransferType(); // 转账类型
        BigDecimal amount = row.getAmount(); // 流动额度
        // 不存在该币种，则加入新币种
        Map<String, BigDecimal> txMap;
        if (!dataMap.containsKey(tokenAddr)) {
            txMap = new HashMap<String, BigDecimal>();
        } else {
            txMap = dataMap.get(tokenAddr);
        }
        if (!txMap.containsKey(txType)) {
            txMap.put(txType, amount);
        } else {
            txMap.put(txType, txMap.get(txType).add(amount));
        }
        dataMap.put(tokenAddr, txMap);
        return amount;
    }

    /**
     * 填充用户详情
     *
     * @param list
     * @return
     */
    private List<WalletTxDTO> fillUserInfos(List<WalletTxDTO> list) {
        Set<String> userIds = new HashSet<>();
        for (WalletTxDTO row : list) {
            if (StringUtils.isNotBlank(row.getUserId())) {
                userIds.add(row.getUserId());
            }
        }
        if (userIds.size() == 0) return list;
        //获取用户信息
        ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
        if (result == null) throw new BaseException(BaseResultEnums.BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        Map<String, UserBaseInfoDTO> userMap = result.getData();
        if (userMap.size() == 0) return list;
        for (WalletTxDTO row : list) {
            String userId = row.getUserId();
            BigDecimal gasPrice = row.getGasPrice() == null ? BigDecimal.ZERO : row.getGasPrice();
            row.setRelAmount(row.getAmount().subtract(gasPrice));
            if (userMap.containsKey(userId)) row.setUserBaseInfoDTO(userMap.get(userId));
        }
        return list;
    }

    private WalletTxDTO fillUserInfo(WalletTxDTO row) {
        Set<String> userIds = new HashSet<>();
        userIds.add(row.getUserId());
        //  获取用户信息
        ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
        if (result == null) throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
        Map<String, UserBaseInfoDTO> userMap = result.getData();
        row.setUserBaseInfoDTO(userMap.get(row.getUserId()));
        BigDecimal gasPrice = row.getGasPrice() == null ? BigDecimal.ZERO : row.getGasPrice();
        row.setRelAmount(row.getAmount().subtract(gasPrice));
        return row;
    }

    /***
     * 根据userName查询用户信息
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserByUserName(String userName) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfoByUserName(userName);
        return resultDTO.getData();
    }

    /***
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    private UserBaseInfoDTO selectUserByUserId(String userId) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfoByUserName(userId);
        return resultDTO.getData();
    }
}
