package com.blockchain.server.eth.service;


import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.eth.dto.tx.EthWalletTransferDTO;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthWalletTransferService {

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, EthToken amountCoin,
                             String transferType, Date date);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param amountCoin   转账币种类型
     * @param transferType 转账类型
     * @param status       转账状态
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, EthToken amountCoin,
                             String transferType, int status, Date date);

    /**
     * 插入一条钱包流水记录
     *
     * @param hash         转账唯一标识
     * @param fromAddr     支付地址
     * @param toAddr       接收地址
     * @param amount       转账余额
     * @param gas          转账手续费
     * @param amountCoin   转账币种类型
     * @param gasCoin      手续费币种类型
     * @param transferType 转账类型
     * @param status       转账状态
     * @return
     */
    EthWalletTransfer insert(String hash, String fromAddr, String toAddr, BigDecimal amount, BigDecimal gas,
                             EthToken amountCoin, EthToken gasCoin, String transferType, int status, Date date);


    /**
     * 根据ID查询记录
     *
     * @param txId ID
     * @return
     */
    EthWalletTransfer findById(String txId);

    /**
     * 根据记录ID查询提现记录信息
     *
     * @param txId 记录ID
     * @return
     */
    WalletTxDTO findOutTransfer(String txId);

    /**
     * 根据记录ID查询提现记录信息
     *
     * @param txId 记录ID
     * @return
     */
    WalletTxDTO findInTransfer(String txId);

    /**
     * 钱包提现记录查询
     *
     * @param params 条件参数
     * @return 集合列表
     */
    List<WalletTxDTO> selectOutTransfer(WalletTxParamsDTO params);

    /***
     * 钱包镜像记录查询
     *
     * @param params 条件参数
     * @return 集合列表
     */
    List<WalletTxDTO> selectShopFastTransfer(WalletTxParamsDTO params);

    /**
     * 钱包充值记录查询
     *
     * @param params 条件参数
     * @return 集合列表
     */
    List<WalletTxDTO> selectInTransfer(WalletTxParamsDTO params);

    /**
     * 查询钱包地址，在某个时间区间成功的记录
     *
     * @param addr      地址
     * @param tokenAddr 代币地址
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    EthWalletTxBillDTO selectByAddrAndTime(String addr, String tokenAddr, Date startDate, Date endDate);


    /**
     * 修改记录状态
     *
     * @param id     记录ID
     * @param status 状态
     */
    EthWalletTransfer updateStatus(String id, int status);

    /**
     * 出币处理
     *
     * @param id 記錄ID
     */
    void handleOut(String id, int status);

    /**
     * 打包失败处理
     *
     * @param id
     */
    void handPackError(String id);

    /**
     * 打包成功处理
     *
     * @param id
     */
    void handPackSuccess(String id);

    /**
     * 驳回处理
     * @param id
     */
    void handReject(String id);
}
