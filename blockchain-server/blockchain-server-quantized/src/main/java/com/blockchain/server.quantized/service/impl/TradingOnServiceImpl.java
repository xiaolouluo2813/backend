package com.blockchain.server.quantized.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.common.utils.TimerTaskUtil;
import com.blockchain.server.quantized.entity.QuantizedSymbol;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.mapper.TradingOnMapper;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.SymbolService;
import com.blockchain.server.quantized.service.TradingOnService;
import com.huobi.client.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-04-18 16:28
 **/
@Service
public class TradingOnServiceImpl implements TradingOnService {

    private static final Logger LOG = LoggerFactory.getLogger(TradingOnServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${quantized.app-addUrl}")
    public String ADDURL;
    @Value("${quantized.app-unUrl}")
    public String UNURL;

    @Autowired
    private TradingOnMapper tradingOnMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SymbolService symbolService;

    @Autowired
    private TimerTaskUtil timerTaskUtil;

    @Override
    public List<TradingOn> list(String state) {
        if (state!=null){
        TradingOn trading = new TradingOn();
        trading.setState(state);
            return tradingOnMapper.select(trading);
        }
        return tradingOnMapper.selectAll();
    }

    @Override
    @Transactional
    public int deleteByCoinNameAndUnitName(String coinName, String unitName) {
        TradingOn trading = selectOne(coinName,unitName);
        if (trading==null){
            throw new QuantizedException(QuantizedResultEnums.TRANSACTION_PAIR_NOT_EXIST);
        }else if (!trading.getState().equals(TradingOnConstant.STATE_CANCEL)){
            throw new QuantizedException(QuantizedResultEnums.TRANSACTION_NOT_DELETE);
        }
        tradingOnMapper.delete(trading);
        return 0;
    }

    @Override
    @Transactional
    public int add(String coinName, String unitName, String state) {
        //查询火币交易对
        QuantizedSymbol quantizedSymbol = new QuantizedSymbol();
        quantizedSymbol.setSymbol(coinName+unitName);
        QuantizedSymbol symbol = symbolService.selectOne(quantizedSymbol);
        //火币交易对不存在
        if (symbol==null){
            throw new QuantizedException(QuantizedResultEnums.TRANSACTION_PAIR_NOT_EXIST);
        }
        //交易对唯一校验
        TradingOn trading = selectOne(coinName,unitName);
        if (trading!=null){
            throw new QuantizedException(QuantizedResultEnums.TRANSACTION_PAIR_IS_EXIST);
        }
        trading = new TradingOn();
        trading.setId(UUID.randomUUID().toString());
        trading.setCoinName(coinName);
        trading.setUnitName(unitName);
        trading.setState(state);
        trading.setCreateTime(new Date());
        tradingOnMapper.insert(trading);
        return 0;
    }

    @Override
    public int updateState(TradingOn trading, String state) {
        trading.setState(state);
        tradingOnMapper.updateByPrimaryKey(trading);
        //用户发起了取消订阅请求，开始撤销火币订单。
        if (state.equals(TradingOnConstant.STATE_CANCELING)){
            LOG.info("取消订阅，发起火币撤单");
            timerTaskUtil.startCron();
        }
        return 0;
    }

    @Override
    public TradingOn selectOne(String coinName, String unitName) {
        TradingOn trading = new TradingOn();
        trading.setCoinName(coinName);
        trading.setUnitName(unitName);
        return tradingOnMapper.selectOne(trading);
    }

    @Override
    public void updateStateByCancel(TradingOn trading) {
        //获取该交易对全部未完成订单
        List<Order> orderList =  orderService.getOpenOrders(trading.getCoinName()+trading.getUnitName());
        if (orderList!=null && orderList.size() > 0){
            //撤销全部该交易对订单
            orderService.cancelAll(trading.getCoinName()+trading.getUnitName());
           //没有未完成订单则修改交易对状态为已取消订阅
        }else {
            //防止取消完成后，火币还没有推送到app
            try {
                Thread.sleep(60000);
                //rpc调用取消订阅交易对
                unSubscribe(trading.getId());
                //将状态设置为已取消
                updateState(trading,TradingOnConstant.STATE_CANCEL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void addSubscribe(String id){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",id);
        ResponseEntity<ResultDTO> forEntity =restTemplate.postForEntity(ADDURL,multiValueMap, ResultDTO.class);
	System.out.println("***************************");
	System.out.println("***************************");
	System.out.println("***************************"+ADDURL);
	System.out.println("***************************");
	System.out.println("***************************");
        if(forEntity.getBody().getCode() != HttpStatus.OK.value()){
            LOG.info("交易对改变调用dapp模块出错 {}",forEntity);
            throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
        }
    }
    public void unSubscribe(String id){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id",id);
        ResponseEntity<ResultDTO> forEntity =restTemplate.postForEntity(UNURL,multiValueMap, ResultDTO.class);
        if(forEntity.getBody().getCode() != HttpStatus.OK.value()){
            LOG.info("交易对改变调用dapp模块出错 {}",forEntity);
            throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
        }
    }

    @Override
    public TradingOn selectByKey(String id) {
        return tradingOnMapper.selectByPrimaryKey(id);
    }

}
