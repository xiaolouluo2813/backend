package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.common.constant.CoinConstant;
import com.blockchain.server.quantized.entity.QuantizedCoin;
import com.blockchain.server.quantized.mapper.CoinMapper;
import com.blockchain.server.quantized.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Liusd
 * @create: 2019-04-26 10:34
 **/
@Service
public class CoinServiceImpl implements CoinService {

    @Autowired
    CoinMapper coinMapper;

    @Override
    public void saveBaseCoin(String baseCurrency) {
        QuantizedCoin quantizedCoin = new QuantizedCoin();
        quantizedCoin.setType(CoinConstant.BASE);
        quantizedCoin.setName(baseCurrency);
        QuantizedCoin coin = coinMapper.selectOne(quantizedCoin);
        if (coin == null){
            quantizedCoin.setId(UUID.randomUUID().toString());
            quantizedCoin.setCreateTime(new Date());
            coinMapper.insert(quantizedCoin);
        }
    }

    @Override
    public void saveQuoteCoin(String quoteCurrency) {
        QuantizedCoin quantizedCoin = new QuantizedCoin();
        quantizedCoin.setType(CoinConstant.QUOTE);
        quantizedCoin.setName(quoteCurrency);
        QuantizedCoin coin = coinMapper.selectOne(quantizedCoin);
        if (coin == null){
            quantizedCoin.setId(UUID.randomUUID().toString());
            quantizedCoin.setCreateTime(new Date());
            coinMapper.insert(quantizedCoin);
        }
    }

    @Override
    public List<QuantizedCoin> list(String type) {
        QuantizedCoin quantizedCoin = new QuantizedCoin();
        quantizedCoin.setType(type);
        return coinMapper.select(quantizedCoin);
    }
}
