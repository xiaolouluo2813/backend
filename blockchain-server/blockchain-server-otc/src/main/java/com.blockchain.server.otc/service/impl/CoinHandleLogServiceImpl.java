package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.coinhandlelog.InsertCoinHandleLogParamDTO;
import com.blockchain.server.otc.dto.coinhandlelog.ListCoinHandleLogResultDTO;
import com.blockchain.server.otc.entity.CoinHandleLog;
import com.blockchain.server.otc.mapper.CoinHandleLogMapper;
import com.blockchain.server.otc.service.CoinHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CoinHandleLogServiceImpl implements CoinHandleLogService {

    @Autowired
    private CoinHandleLogMapper coinHandleLogMapper;

    @Override
    public List<ListCoinHandleLogResultDTO> listCoinHandleLog(String coinId, String beginTime, String endTime) {
        return coinHandleLogMapper.listCoinHandleLog(coinId, beginTime, endTime);
    }

    @Override
    @Transactional
    public int insertCoinHandleLog(InsertCoinHandleLogParamDTO paramDTO) {
        CoinHandleLog coinHandleLog = new CoinHandleLog();
        coinHandleLog.setId(UUID.randomUUID().toString());
        coinHandleLog.setSysUserId(paramDTO.getSysUserId());
        coinHandleLog.setIpAddress(paramDTO.getIpAddress());
        coinHandleLog.setCoinId(paramDTO.getCoinId());
        coinHandleLog.setCreateTime(new Date());
        coinHandleLog.setBeforeCoinDecimal(paramDTO.getBeforeCoinDecimal());
        coinHandleLog.setBeforeUnitDecimal(paramDTO.getBeforeUnitDecimal());
        coinHandleLog.setBeforeCoinName(paramDTO.getBeforeCoinName());
        coinHandleLog.setBeforeUnitName(paramDTO.getBeforeUnitName());
        coinHandleLog.setBeforeCoinNet(paramDTO.getBeforeCoinNet());
        coinHandleLog.setBeforeStatus(paramDTO.getBeforeStatus());
        coinHandleLog.setAfterCoinDecimal(paramDTO.getAfterCoinDecimal());
        coinHandleLog.setAfterUnitDecimal(paramDTO.getAfterUnitDecimal());
        coinHandleLog.setAfterCoinName(paramDTO.getAfterCoinName());
        coinHandleLog.setAfterUnitName(paramDTO.getAfterUnitName());
        coinHandleLog.setAfterCoinNet(paramDTO.getAfterCoinNet());
        coinHandleLog.setAfterStatus(paramDTO.getAfterStatus());
        return coinHandleLogMapper.insertSelective(coinHandleLog);
    }
}
