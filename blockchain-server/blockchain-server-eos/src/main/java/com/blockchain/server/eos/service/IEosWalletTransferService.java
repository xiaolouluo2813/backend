package com.blockchain.server.eos.service;


import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.eos.dto.EosWalletTxBillDTO;
import com.blockchain.server.eos.entity.WalletTransfer;

import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEosWalletTransferService {

    /**
     * 根据ID查询记录
     *
     * @param txId ID
     * @return
     */
    WalletTransfer findById(String txId);

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
    EosWalletTxBillDTO selectByAddrAndTime(String addr, String tokenAddr, Date startDate, Date endDate);

    /**
     * 修改记录状态
     *
     * @param id     记录ID
     * @param status 状态
     */
    WalletTransfer updateStatus(String id, int status);

    /**
     * 插入记录
     *
     * @param walletTransfer
     * @return
     */
    int insertWalletTransfer(WalletTransfer walletTransfer);

    /**
     * @param id
     * @param status
     */
    void handleOut(String id, int status);

    /**
     * 打包失败
     *
     * @param id 记录标识
     */
    void handPackError(String id);

    /**
     * 打包成功
     *
     * @param id 记录标识
     */
    void handPackSuccess(String id);

    /**
     * 驳回处理
     *
     * @param id
     */
    void handReject(String id);
}
