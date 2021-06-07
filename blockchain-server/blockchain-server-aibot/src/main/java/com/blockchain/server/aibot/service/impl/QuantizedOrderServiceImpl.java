package com.blockchain.server.aibot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.entity.QuantizedOrder;
import com.blockchain.server.aibot.mapper.QuantizedOrderMapper;
import com.blockchain.server.aibot.service.QuantizedOrderService;

@Service
public class QuantizedOrderServiceImpl implements QuantizedOrderService {

	private static final Logger LOG = LoggerFactory.getLogger(QuantizedOrderServiceImpl.class);

	@Autowired
	private QuantizedOrderMapper quantizedOrderMapper;

	@Override
	public QuantizedOrder selectByCctId(String cctId) {
		QuantizedOrder quantizedOrder = new QuantizedOrder();
		quantizedOrder.setCctId(cctId);
		QuantizedOrder order = quantizedOrderMapper.selectOne(quantizedOrder);
		return order;
	}

	@Override
	public QuantizedOrder selectByPrimaryKey(long orderId) {
		return quantizedOrderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int insert(QuantizedOrder quantizedOrder) {
		LOG.info("下单成功、新增本地订单记录");
		return quantizedOrderMapper.insert(quantizedOrder);
	}

}
