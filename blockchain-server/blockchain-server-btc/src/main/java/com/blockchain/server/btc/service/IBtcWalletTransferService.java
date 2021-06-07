package com.blockchain.server.btc.service;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.dto.BtcWalletTxBillDTO;
import com.blockchain.server.btc.entity.BtcWalletTransfer;

import java.util.Date;
import java.util.List;

public interface IBtcWalletTransferService {

    /**
     * 插入一条记录
     *
     * @param btcWalletTransfer
     * @return
     */
    Integer insertTransfer(BtcWalletTransfer btcWalletTransfer);

    /**
     * 根据ID查询记录
     *
     * @param txId ID
     * @return
     */
    BtcWalletTransfer findById(String txId);

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
    BtcWalletTxBillDTO selectByAddrAndTime(String addr, String tokenAddr, Date startDate, Date endDate);

    /**
     * 修改记录状态
     *
     * @param id     记录ID
     * @param status 状态
     */
    BtcWalletTransfer updateStatus(String id, int status);

    /**
     * 出币处理
     *
     * @param id 記錄ID
     */
    void handleOut(String id, int status);

    /**
     * 处理CCT交易可用、冻结余额加减
     *
     * @param walletOrderDTO 币币交易，冻结、解冻参数
     * @return
     */
    BtcWalletDTO handleOrder(WalletOrderDTO walletOrderDTO);

    /**
     * 处理加减可用、冻结余额，及其总额
     *
     * @param walletChangeDTO 变动参数
     * @return
     */
    Integer handleChange(WalletChangeDTO walletChangeDTO);

    /**
     * 打包失败处理
     *
     * @param id 记录ID
     */
    void handPackError(String id);

    /**
     * 打包成功处理
     *
     * @param id 记录ID
     */
    void handPackSuccess(String id);

    /**
     * 驳回处理
     *
     * @param id 记录ID
     */
    void handReject(String id);
}
