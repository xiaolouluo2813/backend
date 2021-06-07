package com.blockchain.server.aibot.service;

/**
 * 执行交易策略
 * 
 * @author Administrator
 *
 */
public interface TradingStrategyService {

	/**
	 * 开始策略交易
	 */
	void tradingStrategy(String id);

	/**
	 * 关闭策略交易定时器
	 */
	void closeTradingStrategy(String id);

	/**
	 * 查询策略交易订单定时器
	 */
//	void tradingStrategySchedule();

}
