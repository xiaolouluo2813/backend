package com.blockchain.server.btc.service;

import com.blockchain.server.btc.dto.BtcCountTotalInfoDTO;
import com.blockchain.server.btc.entity.BtcClearingCountTotal;

import java.util.List;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcClearingCountTotalService {

    /**
     * 获取最新的记录
     *
     * @return
     */
    BtcClearingCountTotal findTotalLast(String coinName);

    /**
     * 所有币种最新统计的记录
     *
     */
    List<BtcCountTotalInfoDTO> selectInfoAll();

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
    List<BtcCountTotalInfoDTO> insertTotals();

    /**
     * 插入一条记录
     *
     * @param countTotal
     */
    void insert(BtcClearingCountTotal countTotal);


}
