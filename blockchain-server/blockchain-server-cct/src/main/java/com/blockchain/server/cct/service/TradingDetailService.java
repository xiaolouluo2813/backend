package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.tradingDetail.ListDetailResultDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatParamDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatResultDTO;

import java.util.List;

public interface TradingDetailService {

    /***
     * 查询成交详情列表
     * @param orderId
     * @return
     */
    List<ListDetailResultDTO> listDetailByOrderId(String orderId);

    /***
     * 统计用户交易数据
     * @param param
     * @return
     */
    SelectStatResultDTO selectStatByUserId(SelectStatParamDTO param);
}
