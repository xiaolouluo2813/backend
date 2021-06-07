package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.TradingOn;

import java.util.List;
import java.util.Map;

/**
 * @author: Liusd
 * @create: 2019-04-18 16:28
 **/
public interface TradingOnService {

    List<TradingOn> list(String state);


    int deleteByCoinNameAndUnitName(String coinName, String unitName);

    int add(String coinName, String unitName, String state);

    int updateState(TradingOn tradingOn, String state);

    TradingOn selectOne(String coinName, String unitName);

    void updateStateByCancel(TradingOn trading);

    void addSubscribe(String id);

    TradingOn selectByKey(String id);
}
