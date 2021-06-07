package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.entity.QuantizedSymbol;
import com.blockchain.server.quantized.mapper.SymbolMapper;
import com.blockchain.server.quantized.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-04-26 10:32
 **/
@Service
public class SymbolServiceImpl implements SymbolService {

    @Autowired
    SymbolMapper symbolMapper;

    @Override
    public void saveSymbol(String symbol) {
        QuantizedSymbol quantizedSymbol = new QuantizedSymbol();
        quantizedSymbol.setSymbol(symbol);
        QuantizedSymbol newQuantizedSymbol = symbolMapper.selectOne(quantizedSymbol);
        if (newQuantizedSymbol == null){
            quantizedSymbol.setId(UUID.randomUUID().toString());
            quantizedSymbol.setCreateTime(new Date());
            symbolMapper.insert(quantizedSymbol);
        }
    }

    @Override
    public QuantizedSymbol selectOne(QuantizedSymbol quantizedSymbol) {
        return symbolMapper.selectOne(quantizedSymbol);
    }
}
