package com.blockchain.server.aibot.mapper;

import org.springframework.stereotype.Repository;

import com.blockchain.server.aibot.entity.OrderErr;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderErrMapper extends Mapper<OrderErr> {
}
