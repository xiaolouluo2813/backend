package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.QuantizedCoin;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-26 10:32
 **/
public interface CoinService {
    void saveBaseCoin(String baseCurrency);

    void saveQuoteCoin(String quoteCurrency);

    List<QuantizedCoin> list(String type);
}
