package com.blockchain.server.currency.service.impl;

import com.blockchain.server.currency.common.enums.CurrencyEnums;
import com.blockchain.server.currency.common.exception.CurrencyException;
import com.blockchain.server.currency.entity.Currency;
import com.blockchain.server.currency.mapper.CurrencyMapper;
import com.blockchain.server.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyMapper currencyMapper;

    @Override
    public List<Currency> listCurrency(String coinName, Integer status) {
        return currencyMapper.listCurrency(coinName, status);
    }

    @Override
    @Transactional
    public int insertCurrency(Currency currency) {
        Currency selectObj = currencyMapper.selectByCoinName(currency.getCurrencyName());
        //防止重复
        if (selectObj != null) {
            throw new CurrencyException(CurrencyEnums.CURRENCY_EXIST);
        }
        return currencyMapper.insertSelective(currency);
    }

    @Override
    @Transactional
    public int updateCurrency(Currency currency) {
        Currency selectObj = currencyMapper.selectByCoinName(currency.getCurrencyName());
        //防空
        if (selectObj == null) {
            throw new CurrencyException(CurrencyEnums.CURRENCY_NULL);
        }
        return currencyMapper.updateByPrimaryKeySelective(currency);
    }
}
