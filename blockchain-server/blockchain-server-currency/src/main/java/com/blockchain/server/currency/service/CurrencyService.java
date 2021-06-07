package com.blockchain.server.currency.service;

import com.blockchain.server.currency.entity.Currency;

import java.util.List;

public interface CurrencyService {

    /***
     * 查询所有币种
     * @param coinName
     * @param status
     * @return
     */
    List<Currency> listCurrency(String coinName, Integer status);

    /***
     * 插入行情
     * @param currency
     * @return
     */
    int insertCurrency(Currency currency);

    /***
     * 更新行情
     * @param currency
     * @return
     */
    int updateCurrency(Currency currency);
}
