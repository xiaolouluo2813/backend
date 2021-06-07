package com.blockchain.server.aibot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.blockchain.server.aibot.dto.TradingStrategyHandleLogResultDTO;
import com.blockchain.server.aibot.entity.TradingStrategyHandleLog;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TradingStrategyHandleLogMapper extends Mapper<TradingStrategyHandleLog> {

	/**
	 * 查询交易策略操作日志列表
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<TradingStrategyHandleLogResultDTO> listTradingStrategyHandleLog(@Param("handleType") String handleType,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);

}
