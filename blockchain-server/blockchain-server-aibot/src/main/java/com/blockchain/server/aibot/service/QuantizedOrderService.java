package com.blockchain.server.aibot.service;

import com.blockchain.server.aibot.entity.QuantizedOrder;

public interface QuantizedOrderService {

    int insert(QuantizedOrder quantizedOrder);
    
	QuantizedOrder selectByCctId(String orderId);

    QuantizedOrder selectByPrimaryKey(long orderId);

}
