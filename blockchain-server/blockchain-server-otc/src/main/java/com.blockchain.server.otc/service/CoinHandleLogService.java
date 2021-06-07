package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.coinhandlelog.InsertCoinHandleLogParamDTO;
import com.blockchain.server.otc.dto.coinhandlelog.ListCoinHandleLogResultDTO;

import java.util.List;

public interface CoinHandleLogService {

    /***
     * 查询币种操作日志列表
     * @param coinId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListCoinHandleLogResultDTO> listCoinHandleLog(String coinId, String beginTime, String endTime);

    /***
     * 新增币种操作日志
     * @param paramDTO
     * @return
     */
    int insertCoinHandleLog(InsertCoinHandleLogParamDTO paramDTO);
}
