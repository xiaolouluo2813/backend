package com.blockchain.server.aibot.service;

import java.util.List;

import com.blockchain.server.aibot.dto.TradingStrategyHandleLogResultDTO;
import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.entity.TradingStrategy;

public interface TradingStrategyHandleLogService {

	/**
	 * 查询交易策略操作日志列表
	 * 
	 * @param coin
	 * @param state
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<TradingStrategyHandleLogResultDTO> listTradingStrategyHandleLog(String handleType, String beginTime,
			String endTime);

	/**
	 * 插入新增操作日志
	 * 
	 * @param tradingStrategy
	 * @param sysUserId
	 * @param ipAddress
	 */
	int insertInsertHandleLog(TradingStrategy tradingStrategy, String sysUserId, String ipAddress);

	/**
	 * 插入修改操作日志
	 * 
	 * @param paramDTO
	 * @param tradingStrategy
	 * @param sysUserId
	 * @param ipAddress
	 * @return
	 */
	int insertUpdateHandleLog(TradingStrategyParamDTO paramDTO, TradingStrategy tradingStrategy, String sysUserId,
			String ipAddress);

}
