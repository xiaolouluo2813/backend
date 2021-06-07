package com.blockchain.server.databot.service.impl;

import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.entity.CurrencyConfigHandleLog;
import com.blockchain.server.databot.mapper.CurrencyConfigHandleLogMapper;
import com.blockchain.server.databot.service.CurrencyConfigHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CurrencyConfigHandleLogServiceImpl implements CurrencyConfigHandleLogService {

    @Autowired
    private CurrencyConfigHandleLogMapper configHandleLogMapper;

    @Override
    @Transactional
    public int insertUpdateHandleLog(UpdateConfigParamDTO paramDTO, CurrencyConfig config, String sysUserId, String ipAddress) {
        CurrencyConfigHandleLog configHandleLog = new CurrencyConfigHandleLog();
        configHandleLog.setId(UUID.randomUUID().toString());
        configHandleLog.setCreateTime(new Date());
        configHandleLog.setCurrencyPair(config.getCurrencyPair());
        configHandleLog.setSysUserId(sysUserId);
        configHandleLog.setIpAddress(ipAddress);
        configHandleLog.setHandleType(CommonConstant.UPDATE);

        configHandleLog.setBeforeKChangePercent(config.getKchangePercent());
        configHandleLog.setBeforeKMaxChangePercent(config.getKmaxChangePercent());
        configHandleLog.setBeforeKDayTotalAmount(config.getKdayTotalAmount());
        configHandleLog.setBeforeKMaxPrice(config.getKmaxPrice());
        configHandleLog.setBeforeKMinPrice(config.getKminPrice());
        configHandleLog.setBeforeBuyPricePercent(config.getBuyPricePercent());
        configHandleLog.setBeforeBuyTotalAmount(config.getBuyTotalAmount());
        configHandleLog.setBeforeBuyMaxPrice(config.getBuyMaxPrice());
        configHandleLog.setBeforeBuyMinPrice(config.getBuyMinPrice());
        configHandleLog.setBeforeSellPricePercent(config.getSellPricePercent());
        configHandleLog.setBeforeSellTotalAmount(config.getSellTotalAmount());
        configHandleLog.setBeforeSellMaxPrice(config.getSellMaxPrice());
        configHandleLog.setBeforeSellMinPrice(config.getSellMinPrice());
        configHandleLog.setBeforePriceType(config.getPriceType());

        configHandleLog.setAfterKChangePercent(paramDTO.getKchangePercent());
        configHandleLog.setAfterKMaxChangePercent(paramDTO.getKmaxChangePercent());
        configHandleLog.setAfterKDayTotalAmount(paramDTO.getKdayTotalAmount());
        configHandleLog.setAfterKMaxPrice(paramDTO.getKmaxPrice());
        configHandleLog.setAfterKMinPrice(paramDTO.getKminPrice());
        configHandleLog.setAfterBuyPricePercent(paramDTO.getBuyPricePercent());
        configHandleLog.setAfterBuyTotalAmount(paramDTO.getBuyTotalAmount());
        configHandleLog.setAfterBuyMaxPrice(paramDTO.getBuyMaxPrice());
        configHandleLog.setAfterBuyMinPrice(paramDTO.getBuyMinPrice());
        configHandleLog.setAfterSellPricePercent(paramDTO.getSellPricePercent());
        configHandleLog.setAfterSellTotalAmount(paramDTO.getSellTotalAmount());
        configHandleLog.setAfterSellMaxPrice(paramDTO.getBuyMaxPrice());
        configHandleLog.setAfterSellMinPrice(paramDTO.getBuyMinPrice());
        configHandleLog.setAfterPriceType(paramDTO.getPriceType());

        return configHandleLogMapper.insertSelective(configHandleLog);
    }

    @Override
    @Transactional
    public int insertInsertHandleLog(CurrencyConfig config, String sysUserId, String ipAddress) {
        CurrencyConfigHandleLog configHandleLog = new CurrencyConfigHandleLog();
        configHandleLog.setId(UUID.randomUUID().toString());
        configHandleLog.setCreateTime(new Date());
        configHandleLog.setCurrencyPair(config.getCurrencyPair());
        configHandleLog.setSysUserId(sysUserId);
        configHandleLog.setIpAddress(ipAddress);
        configHandleLog.setHandleType(CommonConstant.INSERT);

        configHandleLog.setAfterKChangePercent(config.getKchangePercent());
        configHandleLog.setAfterKMaxChangePercent(config.getKmaxChangePercent());
        configHandleLog.setAfterKDayTotalAmount(config.getKdayTotalAmount());
        configHandleLog.setAfterKMaxPrice(config.getKmaxPrice());
        configHandleLog.setAfterKMinPrice(config.getKminPrice());
        configHandleLog.setAfterBuyPricePercent(config.getBuyPricePercent());
        configHandleLog.setAfterBuyTotalAmount(config.getBuyTotalAmount());
        configHandleLog.setAfterBuyMaxPrice(config.getBuyMaxPrice());
        configHandleLog.setAfterBuyMinPrice(config.getBuyMinPrice());
        configHandleLog.setAfterSellPricePercent(config.getSellPricePercent());
        configHandleLog.setAfterSellTotalAmount(config.getSellTotalAmount());
        configHandleLog.setAfterSellMaxPrice(config.getBuyMaxPrice());
        configHandleLog.setAfterSellMinPrice(config.getBuyMinPrice());
        configHandleLog.setAfterPriceType(config.getPriceType());

        return configHandleLogMapper.insertSelective(configHandleLog);
    }

    @Override
    public List<ListConfigHandleLogResultDTO> listConfigHandleLog(String currencyPair, String handleType, String beginTime, String endTime) {
        return configHandleLogMapper.listConfigHandleLog(currencyPair, handleType, beginTime, endTime);
    }
}
