package com.blockchain.server.eos.mapper;

import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.eos.dto.WalletTransferDTO;
import com.blockchain.server.eos.entity.WalletTransfer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/2/18 11:13
 * @user WIN10
 */
@Repository
public interface WalletTransferMapper extends Mapper<WalletTransfer> {
    /**
     * 钱包提现查询
     *
     * @param id
     * @return
     */
    WalletTxDTO findOutTx(@Param("id") String id);

    /**
     * 钱包充值查询
     *
     * @param id
     * @return
     */
    WalletTxDTO findInTx(@Param("id") String id);

    /**
     * 钱包提现查询
     *
     * @param params 参数条件
     * @return
     */
    List<WalletTxDTO> selectOutTx(@Param("params") WalletTxParamsDTO params);

    /**
     * 钱包充值查询
     *
     * @param params 参数条件
     * @return
     */
    List<WalletTxDTO> selectInTx(@Param("params") WalletTxParamsDTO params);

    /**
     * 查询多个地址，在某个时间区间成功的记录
     *
     * @param id      地址
     * @param tokenName 代币地址
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    List<WalletTransferDTO> selectByAddrAndTime(@Param("id") String id,
                                                @Param("tokenName") String tokenName,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

    WalletTransfer findByIdForUpdate(@Param("id") String id);
}
