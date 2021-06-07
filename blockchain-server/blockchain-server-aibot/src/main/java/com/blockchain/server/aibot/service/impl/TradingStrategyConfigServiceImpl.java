package com.blockchain.server.aibot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.common.enums.QuantizedResultEnums;
import com.blockchain.server.aibot.common.exception.QuantizedException;
import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.entity.TradingStrategy;
import com.blockchain.server.aibot.mapper.TradingStrategyMapper;
import com.blockchain.server.aibot.service.TradingStrategyConfigService;
import com.blockchain.server.aibot.service.TradingStrategyHandleLogService;

@Service
public class TradingStrategyConfigServiceImpl implements TradingStrategyConfigService {

	@Autowired
	private TradingStrategyMapper tradingStrategyMapper;
	@Autowired
	private TradingStrategyHandleLogService tradingStrategyHandleLogService;

	@Override
	public List<TradingStrategy> list(String coin, String state) {
		return tradingStrategyMapper.selectByCoinAndState(coin, state);
	}

	@Override
	public int insert(TradingStrategyParamDTO paramDTO, String sysUserId, String ipAddress) {
		TradingStrategy tradingStrategy = new TradingStrategy();
		Date now = new Date();
		tradingStrategy.setId(UUID.randomUUID().toString());
		tradingStrategy.setCoin(paramDTO.getCoin());
		tradingStrategy.setApiKey(paramDTO.getApiKey());
		tradingStrategy.setSecretKey(paramDTO.getSecretKey());
		tradingStrategy.setStartupFunds(paramDTO.getStartupFunds());
		tradingStrategy.setGridNum(paramDTO.getGridNum());
		tradingStrategy.setSpreads(paramDTO.getSpreads());
		tradingStrategy.setCreateTime(now);
		tradingStrategy.setModifyTime(now);
		int row = tradingStrategyMapper.insertSelective(tradingStrategy);
		// 插入成功
		if (row == 1) {
			tradingStrategyHandleLogService.insertInsertHandleLog(tradingStrategy, sysUserId, ipAddress);
		} else {
			throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
		}
		return row;
	}

	@Override
	public int update(TradingStrategyParamDTO paramDTO, String sysUserId, String ipAddress) {
		TradingStrategy tradingStrategy = tradingStrategyMapper.selectByPrimaryKey(paramDTO.getId());
		int row = tradingStrategyMapper.updateTradingStrategy(paramDTO, new Date());
		// 更新成功
		if (row == 1) {
			tradingStrategyHandleLogService.insertUpdateHandleLog(paramDTO, tradingStrategy, sysUserId, ipAddress);
		} else {
			throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
		}
		return row;
	}

}
