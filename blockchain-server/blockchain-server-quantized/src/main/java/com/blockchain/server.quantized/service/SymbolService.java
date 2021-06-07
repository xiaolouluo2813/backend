package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.QuantizedSymbol;

/**
 * @author: Liusd
 * @create: 2019-04-26 10:32
 **/
public interface SymbolService {

    void saveSymbol(String symbol);

    QuantizedSymbol selectOne(QuantizedSymbol quantizedSymbol);
}
