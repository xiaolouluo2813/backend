package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.tradingRecord.ListRecordParamDTO;
import com.blockchain.server.cct.dto.tradingRecord.ListRecordResultDTO;

import java.util.List;

public interface TradingRecordService {

    /***
     * 根据订单id查询成交记录+详情
     * @param orderId
     * @return
     */
    List<ListRecordResultDTO> listRecord(String orderId);

    /***
     * 根据id查询
     * @param id
     * @return
     */
    ListRecordResultDTO getById(String id);
}
