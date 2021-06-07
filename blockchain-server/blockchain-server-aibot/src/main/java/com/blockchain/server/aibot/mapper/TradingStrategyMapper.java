package com.blockchain.server.aibot.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.blockchain.server.aibot.dto.TradingStrategyParamDTO;
import com.blockchain.server.aibot.entity.TradingStrategy;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TradingStrategyMapper extends Mapper<TradingStrategy> {

	List<TradingStrategy> selectByCoinAndState(@Param("coin") String coin, @Param("state") String state);

	int updateTradingStrategy(@Param("paramDTO") TradingStrategyParamDTO paramDTO,
			@Param("modifyTime") Date modifyTime);

}
