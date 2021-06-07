package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.common.constant.OrderCancelConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.blockchain.server.quantized.service.*;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.ExchangeInfo;
import com.huobi.client.model.Order;
import com.huobi.client.model.Symbol;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.request.CancelOpenOrderRequest;
import com.huobi.client.model.request.OpenOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author: Liusd
 * @create: 2019-04-18 19:34
 **/
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private QuantizedOrderService quantizedOrderService;

    @Override
    @Transactional
    public String cancellations(String symbol, Long orderId) {
        //账号信息
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        try {
            syncRequestClient.cancelOrder(symbol, orderId);
            return OrderCancelConstant.SUCCESS;
        } catch (Exception e) {
            LOG.info(new QuantizedException(QuantizedResultEnums.CANCEL_ORDER_ERROR).getMsg() + ",参数为 ：symbol :{},orderId : {} ,异常原因为 ：{}", symbol, orderId, e.getMessage());
            return OrderCancelConstant.FAIL;
        }
    }

    @Override
    @Transactional
    public void initOrder() {
        //账号信息
        SyncRequestClient syncRequestClient = SyncRequestClient.create();
        //初始化交易对
        getSymbols(syncRequestClient).forEach(symbol -> {
            //交易对
            symbolService.saveSymbol(symbol.getSymbol());
            //基础币
            coinService.saveBaseCoin(symbol.getBaseCurrency());
            //保价币
            coinService.saveQuoteCoin(symbol.getQuoteCurrency());
        });
    }

    @Override
    public List<Order> getOpenOrders(String symbol) {
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        OpenOrderRequest openOrderRequest = new OpenOrderRequest(symbol,
                AccountType.SPOT);
        List<Order> orderList = syncRequestClient.getOpenOrders(openOrderRequest);
        return orderList;
    }

    @Override
    public BatchCancelResult cancelAll(String symbol) {
        SyncRequestClient syncRequestClient = SyncRequestClient.create(getAccount().getApiKey(), getAccount().getSecretKey());
        CancelOpenOrderRequest cancelOpenOrderRequest = new CancelOpenOrderRequest(symbol,AccountType.SPOT);
        BatchCancelResult batchCancelResult = syncRequestClient.cancelOpenOrders(cancelOpenOrderRequest);
        return batchCancelResult;
    }

    @Override
    public String cancel(String cctId) {
        QuantizedOrder quantizedOrder = quantizedOrderService.selectByCctId(cctId);
        if (quantizedOrder==null){
            return OrderCancelConstant.OVER;
        }
        return cancellations(quantizedOrder.getSymbol(),quantizedOrder.getId());
    }

    //火币账户
    private QuantizedAccount getAccount(){
        return accountService.findOne();
    }
    //获取全部交易对
    private List<Symbol> getSymbols(SyncRequestClient syncRequestClient){
        ExchangeInfo exchangeInfo = syncRequestClient.getExchangeInfo();
        return exchangeInfo.getSymbolList();
    }
}
