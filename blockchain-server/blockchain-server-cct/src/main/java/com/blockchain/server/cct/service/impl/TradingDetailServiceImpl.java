package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.dto.tradingDetail.ListDetailResultDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatParamDTO;
import com.blockchain.server.cct.dto.tradingDetail.SelectStatResultDTO;
import com.blockchain.server.cct.mapper.TradingDetailMapper;
import com.blockchain.server.cct.service.TradingDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TradingDetailServiceImpl implements TradingDetailService {

    @Autowired
    private TradingDetailMapper detailMapper;

    @Override
    public List<ListDetailResultDTO> listDetailByOrderId(String orderId) {
        return detailMapper.listDetailByOrderId(orderId);
    }

    @Override
    public SelectStatResultDTO selectStatByUserId(SelectStatParamDTO param) {
        return detailMapper.selectStatByUserId(param);
    }
}
