package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.CoinLog;

import java.util.List;

public interface CoinLogService {
    /***
     * 插入交易对操作日志记录
     * @param log
     * @return
     */
    int insertCoinLog(CoinLog log);

    /***
     * 交易对操作日志列表
     * @return
     */
    List<CoinLog> listCoinLog();
}
