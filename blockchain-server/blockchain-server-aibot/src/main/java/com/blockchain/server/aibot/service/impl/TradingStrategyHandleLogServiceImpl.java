package com.blockchain.server.aibot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.common.constant.AibotConstant;
import com.blockchain.server.aibot.dto.TradingStrategyHandleLogResultDTO;
import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.entity.TradingStrategy;
import com.blockchain.server.aibot.entity.TradingStrategyHandleLog;
import com.blockchain.server.aibot.mapper.TradingStrategyHandleLogMapper;
import com.blockchain.server.aibot.service.TradingStrategyHandleLogService;

@Service
public class TradingStrategyHandleLogServiceImpl implements TradingStrategyHandleLogService {

	@Autowired
	private TradingStrategyHandleLogMapper tradingStrategyHandleLogMapper;

	@Override
	public List<TradingStrategyHandleLogResultDTO> listTradingStrategyHandleLog(String handleType, String beginTime,
			String endTime) {
		return tradingStrategyHandleLogMapper.listTradingStrategyHandleLog(handleType, beginTime, endTime);
	}

	@Override
	public int insertInsertHandleLog(TradingStrategy tradingStrategy, String sysUserId, String ipAddress) {
		TradingStrategyHandleLog tradingStrategyHandleLog = new TradingStrategyHandleLog();
		tradingStrategyHandleLog.setId(UUID.randomUUID().toString());
		tradingStrategyHandleLog.setCreateTime(new Date());
		tradingStrategyHandleLog.setHandleType(AibotConstant.INSERT);
		tradingStrategyHandleLog.setSysUserId(sysUserId);
		tradingStrategyHandleLog.setIpAddress(ipAddress);
		tradingStrategyHandleLog.setAfterCoin(tradingStrategy.getCoin());
		tradingStrategyHandleLog.setAfterApiKey(tradingStrategy.getApiKey());
		tradingStrategyHandleLog.setAfterSecretKey((tradingStrategy.getSecretKey()));
		tradingStrategyHandleLog.setAfterStartupFunds(tradingStrategy.getStartupFunds());
		tradingStrategyHandleLog.setAfterGridNum(tradingStrategy.getGridNum());
		tradingStrategyHandleLog.setAfterSpreads(tradingStrategy.getSpreads());
		tradingStrategyHandleLog.setAfterState(tradingStrategy.getState());
		return tradingStrategyHandleLogMapper.insertSelective(tradingStrategyHandleLog);
	}

	@Override
	public int insertUpdateHandleLog(TradingStrategyParamDTO paramDTO, TradingStrategy tradingStrategy,
			String sysUserId, String ipAddress) {
		TradingStrategyHandleLog tradingStrategyHandleLog = new TradingStrategyHandleLog();
		tradingStrategyHandleLog.setId(UUID.randomUUID().toString());
		tradingStrategyHandleLog.setCreateTime(new Date());
		tradingStrategyHandleLog.setHandleType(AibotConstant.UPDATE);
		tradingStrategyHandleLog.setSysUserId(sysUserId);
		tradingStrategyHandleLog.setIpAddress(ipAddress);

		tradingStrategyHandleLog.setBeforeCoin(tradingStrategy.getCoin());
		tradingStrategyHandleLog.setBeforeApiKey(tradingStrategy.getApiKey());
		tradingStrategyHandleLog.setBeforeSecretKey((tradingStrategy.getSecretKey()));
		tradingStrategyHandleLog.setBeforeStartupFunds(tradingStrategy.getStartupFunds());
		tradingStrategyHandleLog.setBeforeGridNum(tradingStrategy.getGridNum());
		tradingStrategyHandleLog.setBeforeSpreads(tradingStrategy.getSpreads());
		tradingStrategyHandleLog.setBeforeState(tradingStrategy.getState());

		tradingStrategyHandleLog.setAfterCoin(paramDTO.getCoin());
		tradingStrategyHandleLog.setAfterApiKey(paramDTO.getApiKey());
		tradingStrategyHandleLog.setAfterSecretKey((paramDTO.getSecretKey()));
		tradingStrategyHandleLog.setAfterStartupFunds(paramDTO.getStartupFunds());
		tradingStrategyHandleLog.setAfterGridNum(paramDTO.getGridNum());
		tradingStrategyHandleLog.setAfterSpreads(paramDTO.getSpreads());
		tradingStrategyHandleLog.setAfterState(paramDTO.getState());

		return tradingStrategyHandleLogMapper.insertSelective(tradingStrategyHandleLog);
	}

}
