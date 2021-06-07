package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.dto.QuantizedOrderDto;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.huobi.client.model.Order;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-19 16:51
 **/
public interface QuantizedOrderService {
    int insert(QuantizedOrder quantizedOrder);

    List<QuantizedOrderDto> list(String mobilePhone, String state, String type);

    int Update(Order order);

    QuantizedOrder selectByCctId(String cctId);
}
