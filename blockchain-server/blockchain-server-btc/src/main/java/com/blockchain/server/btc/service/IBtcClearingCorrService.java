package com.blockchain.server.btc.service;

import com.blockchain.server.btc.entity.BtcClearingCorr;

import java.util.List;

/**
 * 更正记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcClearingCorrService {

    /**
     * 查询统计记录的更正记录
     *
     * @param totalId
     * @return
     */
    List<BtcClearingCorr> selectByTotalId(String totalId);

    /**
     * 插入一条更正数据
     *
     * @param btcClearingCorr
     */
    void insert(BtcClearingCorr btcClearingCorr);


}
