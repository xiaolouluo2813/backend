package com.blockchain.server.aibot.service;

import java.math.BigDecimal;

import com.blockchain.server.aibot.entity.TradingStrategy;
import com.huobi.client.model.Order;
import com.huobi.client.model.request.NewOrderRequest;

public interface OrderService {

	Order create(NewOrderRequest newOrderRequest, TradingStrategy tradingStrategy);

	String cancellations(String symbol, Long orderId, TradingStrategy tradingStrategy);

	String cancel(Long orderId, TradingStrategy tradingStrategy);

	Order getOrder(Long orderId, TradingStrategy tradingStrategy);

	BigDecimal getPrice(String symbol);
//	List<MarketDTO> getPriceDepth(String symbol, int size, String type);

	int synchronizeOrder(Order sellOrder);
}
