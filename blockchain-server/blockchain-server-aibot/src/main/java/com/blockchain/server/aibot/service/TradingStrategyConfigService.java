package com.blockchain.server.aibot.service;

import java.util.List;

import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.entity.TradingStrategy;

/**
 * 查询更新交易策略配置
 * 
 * @author ljj
 *
 */
public interface TradingStrategyConfigService {

	/**
	 * 查询交易策略
	 * 
	 * @param coin
	 * @param state
	 * @return
	 */
	List<TradingStrategy> list(String coin, String state);

	/**
	 * 增加交易策略
	 * 
	 * @param paramDTO
	 */
	int insert(TradingStrategyParamDTO paramDTO, String sysUserId, String ipAddress);

	/**
	 * 修改交易策略
	 * 
	 * @param paramDTO
	 */
	int update(TradingStrategyParamDTO paramDTO, String sysUserId, String ipAddress);

}
