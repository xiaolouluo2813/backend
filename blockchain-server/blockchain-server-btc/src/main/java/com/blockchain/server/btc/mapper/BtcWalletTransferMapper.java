package com.blockchain.server.btc.mapper;

import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.btc.dto.BtcWalletTransferDTO;
import com.blockchain.server.btc.entity.BtcWalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * BtcWalletTransferMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Repository
public interface BtcWalletTransferMapper extends Mapper<BtcWalletTransfer> {

    /***
     * 根据id查询提现记录
     * @param txId
     * @return
     */
    WalletTxDTO findOutTx(@Param("id") String txId);

    /***
     * 根据id查询充值记录
     * @param txId
     * @return
     */
    WalletTxDTO findInTx(@Param("id") String txId);

    /***
     * 钱包提现查询
     * @param params 参数条件
     * @return
     */
    List<WalletTxDTO> selectOutTx(@Param("params") WalletTxParamsDTO params);

    /***
     * 钱包充值查询
     * @param params 参数条件
     * @return
     */
    List<WalletTxDTO> selectInTx(@Param("params") WalletTxParamsDTO params);


    /**
     * 查询多个地址，在某个时间区间成功的记录
     *
     * @param addr      地址
     * @param tokenId   代币地址
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<BtcWalletTransferDTO> selectByAddrAndTime(@Param("addr") String addr,
                                                   @Param("tokenId") String tokenId,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);

    BtcWalletTransfer findByIdForUpdate(@Param("id") String id);
}