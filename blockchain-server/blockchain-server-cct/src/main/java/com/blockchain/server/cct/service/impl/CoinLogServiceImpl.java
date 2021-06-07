package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.entity.CoinLog;
import com.blockchain.server.cct.mapper.CoinLogMapper;
import com.blockchain.server.cct.service.CoinLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoinLogServiceImpl implements CoinLogService {

    @Autowired
    private CoinLogMapper logMapper;

    @Override
    @Transactional
    public int insertCoinLog(CoinLog log) {
        return logMapper.insertSelective(log);
    }

    @Override
    public List<CoinLog> listCoinLog() {
        return logMapper.listAllOrderByTimeDESC();
    }
}
