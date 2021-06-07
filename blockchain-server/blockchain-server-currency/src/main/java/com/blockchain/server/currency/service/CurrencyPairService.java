package com.blockchain.server.currency.service;

import com.blockchain.server.currency.entity.CurrencyPair;

import java.util.List;

public interface CurrencyPairService {

    /***
     * 查询行情币对
     * @param currencyPair
     * @param status
     * @return
     */
    List<CurrencyPair> listCurrencyPair(String currencyPair, Integer status);

    /***
     * 插入行情币对
     * @param currencyPair
     * @param status
     * @param orderBy
     * @param isHome
     * @param isCct
     * @return
     */
    @Deprecated
    int insertCurrencyPair(String currencyPair, Integer status, Integer orderBy, Integer isHome, Integer isCct);

    /***
     * 更新行情币对
     * @param currencyPair
     * @param status
     * @param orderBy
     * @param isHome
     * @param isCct
     * @return
     */
    int updateCurrencyPair(String currencyPair, Integer status, Integer orderBy, Integer isHome,
                           Integer isCct, String sysUserId, String ipAdrr);

    /***
     * 用于币币模块更新时，同步行情币对的数据
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    int updateCurrencyPaifOfCct(String coinName, String unitName, String status);
}
