package com.blockchain.server.aibot.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.common.constant.AibotConstant;
import com.blockchain.server.aibot.common.enums.QuantizedResultEnums;
import com.blockchain.server.aibot.common.exception.QuantizedException;
import com.blockchain.server.aibot.entity.TradingStrategy;
import com.blockchain.server.aibot.mapper.TradingStrategyMapper;
import com.blockchain.server.aibot.service.AccountService;
import com.blockchain.server.aibot.service.OrderService;
import com.blockchain.server.aibot.service.QuantizedOrderService;
import com.blockchain.server.aibot.service.TradingStrategyService;
import com.huobi.client.model.Order;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.OrderState;
import com.huobi.client.model.enums.OrderType;
import com.huobi.client.model.request.NewOrderRequest;

@Service
public class TradingStrategyServiceImpl implements TradingStrategyService {

	@Autowired
	private TradingStrategyMapper tradingStrategyMapper;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AccountService accountService;

//	@Autowired
//	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//	private ScheduledFuture<?> future;

//	@Autowired
//	private TradingStrategySchedule tradingStrategySchedule;

	private int isFilled = AibotConstant.ORDER_STATUS_NO_FILLED;// 订单是否完成

	private TradingStrategy tradingStrategy;// 当前执行的交易策略

	private Integer gridNum = 0;// 记录当前交易所在网格数
	private BigDecimal price;// 价差价格
	private BigDecimal unitAmount;// 价差价格
	private Order sellOrder;// 当前的卖单
	private Order buyOrder;// 当前的买单

	/**
	 * 开始交易
	 */
	@Override
	public void tradingStrategy(String id) {
		tradingStrategy = tradingStrategyMapper.selectByPrimaryKey(id);
		if (tradingStrategy == null) {
			throw new QuantizedException(QuantizedResultEnums.TRADING_STRATEGY_NOT_EXIST);
		}
		BigDecimal accountBalance = accountService.getAccountBalance(tradingStrategy,
				tradingStrategy.getCoin().split("-")[1]);
		System.out.println(accountBalance);
		// 启动资金设置 保留1位小数
		BigDecimal startupFounds = accountBalance.multiply(new BigDecimal(tradingStrategy.getStartupFunds()))
				.setScale(1, BigDecimal.ROUND_DOWN);
		System.out.println("-----===== ");
		System.out.println("-----===== ");
		System.out.println("-----===== ");
		System.out.println("-----===== ");
		System.out.println("-----startupFounds===== " + startupFounds);
		// 去火币下单 买入BTC
		String symbol = tradingStrategy.getCoin().replace("-", "").toLowerCase();
		// 市价
		BigDecimal marketPrice = orderService.getPrice(symbol);
		if (marketPrice == null) {
			throw new QuantizedException(QuantizedResultEnums.TRANSACTION_PAIR_NOT_EXIST);
		}
		System.out.println("市价------------- " + marketPrice);
		BigDecimal buyAmount = startupFounds.divide(marketPrice, 4, BigDecimal.ROUND_HALF_UP);
		System.out.println("买入量------------ " + buyAmount);
		NewOrderRequest newOrderRequest = new NewOrderRequest(symbol, AccountType.SPOT, OrderType.BUY_LIMIT, buyAmount,
				marketPrice);
		System.out.println("---------------------------");
		System.out.println(newOrderRequest.getSymbol());
		System.out.println(newOrderRequest.getAmount());
		System.out.println(newOrderRequest.getType());
		System.out.println(newOrderRequest.getPrice());
		System.out.println("---------------------------");
		try {
			orderService.create(newOrderRequest, tradingStrategy);
		} catch (Exception e) {
			throw new QuantizedException(QuantizedResultEnums.CREATE_ORDER_ERROR);
		}
		// 获取价差比例
		BigDecimal spreads = new BigDecimal(tradingStrategy.getSpreads());
		// 根据市价计算各个网格数价格
		price = marketPrice.multiply(spreads);
		unitAmount = buyAmount.divide(new BigDecimal(tradingStrategy.getGridNum())).setScale(4,
				BigDecimal.ROUND_HALF_UP);
		createOrder(marketPrice);
//		openStrategyTransaction();
	}

	/**
	 * 定时查询订单状态下单
	 */
//	@Override
	public void tradingStrategySchedule() {
		BigDecimal transactionPrice = null;
		System.out.println("======isFilled=======");
		System.out.println("======isFilled=======");
		System.out.println("======isFilled=======");
		System.out.println("======isFilled=======");
		System.out.println(isFilled);
		while (isFilled == AibotConstant.ORDER_STATUS_NO_FILLED) {
			// 查询订单状态
			Order sellOrder = orderService.getOrder(this.sellOrder.getOrderId(), tradingStrategy);
			Order buyOrder = orderService.getOrder(this.buyOrder.getOrderId(), tradingStrategy);
			if (sellOrder == null || buyOrder == null) {
				throw new QuantizedException(QuantizedResultEnums.ORDER_DETAILS_ERROR);
			}
			if (sellOrder.getState() == OrderState.FILLED) {
				//同步本地数据
				orderService.synchronizeOrder(sellOrder);
				// 卖单成功，取消买单
				orderService.cancel(buyOrder.getOrderId(), tradingStrategy);
				transactionPrice = sellOrder.getPrice();
				isFilled = AibotConstant.ORDER_STATUS_FILLED;
				gridNum++;
			} else if (buyOrder.getState() == OrderState.FILLED) {
				// 买单成功，取消卖单
				orderService.cancel(sellOrder.getOrderId(), tradingStrategy);
				transactionPrice = buyOrder.getPrice();
				isFilled = AibotConstant.ORDER_STATUS_FILLED;
				gridNum--;
			}
		}
		if (gridNum == Integer.parseInt(tradingStrategy.getGridNum())) {
			// 完成一轮买卖单
			tradingStrategy(tradingStrategy.getId());
		} else {
			if (isFilled == AibotConstant.ORDER_STATUS_FILLED) {
				createOrder(transactionPrice);
			}
		}
	}

	/**
	 * 下单
	 * 
	 * @param transactionPrice
	 * @param strategyTransactionCharge
	 */
	public void createOrder(BigDecimal transactionPrice) {
		// 新挂单价格
		BigDecimal sellPrice = transactionPrice.add(price).setScale(4, BigDecimal.ROUND_HALF_UP);// 高卖
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$sellPrice = " + sellPrice);
		BigDecimal buyPrice = transactionPrice.add(price.negate()).setScale(4, BigDecimal.ROUND_HALF_UP);// 低买
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$");
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$buyPrice = " + buyPrice);
		// 去火币下两单
		// 根据网格数计算每单btc数量
		String coin = tradingStrategy.getCoin().replace("-", "").toLowerCase();
		NewOrderRequest newOrderRequestSell = new NewOrderRequest(coin, AccountType.SPOT, OrderType.SELL_LIMIT,
				unitAmount, sellPrice);
		NewOrderRequest newOrderRequestBuy = new NewOrderRequest(coin, AccountType.SPOT, OrderType.BUY_LIMIT,
				unitAmount, buyPrice);
		try {
			this.sellOrder = orderService.create(newOrderRequestSell, tradingStrategy);
			this.buyOrder = orderService.create(newOrderRequestBuy, tradingStrategy);
		} catch (Exception e) {
			throw new QuantizedException(QuantizedResultEnums.CREATE_ORDER_ERROR);
		}
		isFilled = AibotConstant.ORDER_STATUS_NO_FILLED;
		tradingStrategySchedule();
	}

//	public void openStrategyTransaction() {
//		// 先关闭定时器
//		closeTradingStrategy();
//		// 打开定时器
//		future = new ThreadPoolTaskScheduler().schedule(tradingStrategySchedule, new CronTrigger("*/2 * * * * ?"));
//	}
//
	@Override
	public void closeTradingStrategy(String id) {
		TradingStrategy tradingStrategy = tradingStrategyMapper.selectByPrimaryKey(id);
		// 停止当前策略，取消挂单
		if (buyOrder != null) {
			orderService.cancel(buyOrder.getOrderId(), tradingStrategy);
		}
		if (sellOrder != null) {
			orderService.cancel(sellOrder.getOrderId(), tradingStrategy);
		}
		isFilled = AibotConstant.ORDER_STATUS_STOP;

//		if (future != null) {
//			future.cancel(true);
//		}
	}

}