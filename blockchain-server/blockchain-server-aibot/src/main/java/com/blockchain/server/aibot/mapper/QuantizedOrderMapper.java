package com.blockchain.server.aibot.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.blockchain.server.aibot.entity.QuantizedOrder;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface QuantizedOrderMapper extends Mapper<QuantizedOrder> {

	QuantizedOrder selectByPrimaryKeyForUpdate(@Param("orderId") long orderId);
}
