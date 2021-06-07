package com.blockchain.server.aibot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blockchain.server.aibot.entity.OrderErr;
import com.blockchain.server.aibot.mapper.OrderErrMapper;
import com.blockchain.server.aibot.service.OrderErrService;

@Service
public class OrderErrServiceImpl implements OrderErrService {

	@Autowired
	private OrderErrMapper orderErrMapper;

	@Override
	@Transactional
	public int insert(OrderErr orderErr) {
		return orderErrMapper.insert(orderErr);
	}

}
