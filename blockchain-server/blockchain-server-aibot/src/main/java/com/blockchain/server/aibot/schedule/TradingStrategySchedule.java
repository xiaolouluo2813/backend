package com.blockchain.server.aibot.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blockchain.server.aibot.service.TradingStrategyService;

@Component
public class TradingStrategySchedule implements Runnable {

//	@Autowired
//	private TradingStrategyService tradingStrategyService;
//	@Autowired
//	private TradingStrategyMapper tradingStrategyMapper;

	@Override
	public void run() {
		strategyTransaction();
	}

	/**
	 * 策略交易
	 */
	private void strategyTransaction() {
//		List<TradingStrategy> accountList = tradingStrategyMapper.selectByCoinAndState(null, AibotConstant.STATUS_YES);
//		for (TradingStrategy account : accountList) {
//			tradingStrategyService.tradingStrategy(account.getId());
//		}
//		tradingStrategyService.tradingStrategySchedule();
	}
}
