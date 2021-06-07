package com.blockchain.server.aibot.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.common.constant.OrderCancelConstant;
import com.blockchain.server.aibot.common.enums.QuantizedResultEnums;
import com.blockchain.server.aibot.common.exception.QuantizedException;
import com.blockchain.server.aibot.entity.OrderErr;
import com.blockchain.server.aibot.entity.QuantizedOrder;
import com.blockchain.server.aibot.entity.TradingStrategy;
import com.blockchain.server.aibot.service.OrderErrService;
import com.blockchain.server.aibot.service.OrderService;
import com.blockchain.server.aibot.service.QuantizedOrderService;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.request.NewOrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	private QuantizedOrderService quantizedOrderService;

	@Autowired
	private OrderErrService orderErrService;

	@Override
	public Order create(NewOrderRequest newOrderRequest, TradingStrategy tradingStrategy) {
		try {
			SyncRequestClient syncRequestClient = SyncRequestClient.create(tradingStrategy.getApiKey(),
					tradingStrategy.getSecretKey());
			long orderId = syncRequestClient.createOrder(newOrderRequest);
			// 保存在本地
			QuantizedOrder quantizedOrder = new QuantizedOrder();
			quantizedOrder.setId(orderId);
			quantizedOrder.setAmount(newOrderRequest.getAmount().toString());
			quantizedOrder.setCreatedAt(System.currentTimeMillis());
			quantizedOrder.setPrice(newOrderRequest.getPrice() + "");
			quantizedOrder.setState(OrderState.SUBMITTED.toString());
			quantizedOrder.setSymbol(newOrderRequest.getSymbol());
			quantizedOrder.setType(newOrderRequest.getType().toString());
			// quantizedOrder.setUserId(userId);
			// quantizedOrder.setCctId(cctId);
			quantizedOrderService.insert(quantizedOrder);
			return syncRequestClient.getOrder(newOrderRequest.getSymbol(), orderId);
		} catch (Exception e) {
			throw new QuantizedException(QuantizedResultEnums.CREATE_ORDER_ERROR);
		}
	}

	@Override
	public int synchronizeOrder(Order order) {
		QuantizedOrder quantizedOrder = quantizedOrderService.selectByPrimaryKey(order.getOrderId());
//		quantizedOrder
		return 0;
	}
	
	@Override
	public String cancellations(String symbol, Long orderId, TradingStrategy tradingStrategy) {
		// 账号信息
		SyncRequestClient syncRequestClient = SyncRequestClient.create(tradingStrategy.getApiKey(),
				tradingStrategy.getSecretKey());
		try {
			syncRequestClient.cancelOrder(symbol, orderId);
			return OrderCancelConstant.SUCCESS;
		} catch (Exception e) {
			LOG.info(new QuantizedException(QuantizedResultEnums.CANCEL_ORDER_ERROR).getMsg()
					+ ",参数为 ：symbol :{},orderId : {} ,异常原因为 ：{}", symbol, orderId, e.getMessage());
			return OrderCancelConstant.FAIL;
		}
	}

	@Override
	public String cancel(Long orderId, TradingStrategy tradingStrategy) {
		QuantizedOrder quantizedOrder = quantizedOrderService.selectByPrimaryKey(orderId);
		if (quantizedOrder == null) {
			return OrderCancelConstant.OVER;
		}
		return cancellations(quantizedOrder.getSymbol(), quantizedOrder.getId(), tradingStrategy);
	}

	@Override
	public Order getOrder(Long orderId, TradingStrategy tradingStrategy) {
		SyncRequestClient syncRequestClient = SyncRequestClient.create(tradingStrategy.getApiKey(),
				tradingStrategy.getSecretKey());
		QuantizedOrder quantizedOrder = quantizedOrderService.selectByPrimaryKey(orderId);
		return syncRequestClient.getOrder(quantizedOrder.getSymbol(), orderId);
	}

	@Override
	public BigDecimal getPrice(String symbol) {
		return SyncRequestClient.create().getLastTrade(symbol).getPrice();
	}


//	@Override
//	public List<MarketDTO> getPriceDepth(String symbol, int size, String type) {
//		List<MarketDTO> marketDTOList = new ArrayList<>();
//		PriceDepth priceDepth = SyncRequestClient.create().getPriceDepth(symbol, size);
//		// 买
//		if (type.equals(DepthConstant.BUY)) {
//			priceDepth.getBids().forEach(depth -> {
//				MarketDTO marketDTO = new MarketDTO();
//				marketDTO.setUnitPrice(depth.getPrice());
//				marketDTO.setTotalNum(depth.getAmount());
//				marketDTO.setTotalLastNum(depth.getAmount());
//				marketDTOList.add(marketDTO);
//			});
//			// 卖
//		} else {
//			priceDepth.getAsks().forEach(depth -> {
//				MarketDTO marketDTO = new MarketDTO();
//				marketDTO.setUnitPrice(depth.getPrice());
//				marketDTO.setTotalNum(depth.getAmount());
//				marketDTO.setTotalLastNum(depth.getAmount());
//				marketDTOList.add(marketDTO);
//			});
//		}
//		return marketDTOList;
//	}
}
