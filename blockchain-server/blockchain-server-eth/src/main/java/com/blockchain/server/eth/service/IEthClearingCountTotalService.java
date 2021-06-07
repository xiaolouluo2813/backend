package com.blockchain.server.eth.service;

import com.blockchain.server.eth.dto.wallet.EthCountTotalInfoDTO;
import com.blockchain.server.eth.dto.wallet.EthTotalInfoDTO;
import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.EthClearingCountTotal;
import com.blockchain.server.eth.entity.EthClearingTotal;
import com.blockchain.server.eth.entity.EthWallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthClearingCountTotalService {

    /**
     * 获取最新的记录
     *
     * @return
     */
    EthClearingCountTotal findTotalLast(String coinName);

    /**
     * 所有币种最新统计的记录
     *
     */
    List<EthCountTotalInfoDTO> selectInfoAll();

    /**
     * 生成用户财务记录快照
     *
     * @param coinName 代币名称
     */
    void insertTotal(String coinName);

    /**
     * 生成用户财务记录快照(多个)
     *
     */
    List<EthCountTotalInfoDTO> insertTotals();

    /**
     * 插入一条记录
     *
     * @param countTotal
     */
    void insert(EthClearingCountTotal countTotal);
}
