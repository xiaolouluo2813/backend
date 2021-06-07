package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.dto.tradingRecord.ListRecordParamDTO;
import com.blockchain.server.cct.dto.tradingRecord.ListRecordResultDTO;
import com.blockchain.server.cct.mapper.TradingRecordMapper;
import com.blockchain.server.cct.service.TradingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingRecordServiceImpl implements TradingRecordService {

    @Autowired
    private TradingRecordMapper recordMapper;

    @Override
    public List<ListRecordResultDTO> listRecord(String orderId) {
        return recordMapper.listRecord(orderId);
    }

    @Override
    public ListRecordResultDTO getById(String id) {
        return recordMapper.selectById(id);
    }
}
