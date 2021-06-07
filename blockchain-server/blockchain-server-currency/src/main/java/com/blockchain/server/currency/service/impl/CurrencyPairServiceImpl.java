package com.blockchain.server.currency.service.impl;

import com.blockchain.server.currency.common.constant.CurrencyConstant;
import com.blockchain.server.currency.common.enums.CurrencyEnums;
import com.blockchain.server.currency.common.exception.CurrencyException;
import com.blockchain.server.currency.entity.CurrencyPair;
import com.blockchain.server.currency.feign.CctFeign;
import com.blockchain.server.currency.mapper.CurrencyPairMapper;
import com.blockchain.server.currency.redis.MarketCache;
import com.blockchain.server.currency.service.CurrencyPairService;
import com.codingapi.tx.annotation.TxTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    @Autowired
    private CurrencyPairMapper currencyPairMapper;
    @Autowired
    private CctFeign cctFeign;
    @Autowired
    private MarketCache marketCache;

    //行情币对
    private static final String CURRENCY_PAIR = "{0}-{1}";

    @Override
    public List<CurrencyPair> listCurrencyPair(String currencyPair, Integer status) {
        return currencyPairMapper.listCurrencyPair(currencyPair, status);
    }

    @Override
    @Transactional
    public int insertCurrencyPair(String currencyPair, Integer status, Integer orderBy, Integer isHome, Integer isCct) {
        CurrencyPair currencyPairObj = currencyPairMapper.selectByCurrencyPair(currencyPair);
        //防止重复
        if (currencyPairObj != null) {
            throw new CurrencyException(CurrencyEnums.CURRENCYPAIR_EXIST);
        }
        CurrencyPair currencyPairNewObj = new CurrencyPair();
        currencyPairNewObj.setCurrencyPair(currencyPair);
        currencyPairNewObj.setIsHome(isHome);
        currencyPairNewObj.setIsCct(isCct);
        currencyPairNewObj.setStatus(status);
        currencyPairNewObj.setOrderBy(orderBy);

        //删除缓存
        deleteCacheHomeListAndListList();
        return currencyPairMapper.insertSelective(currencyPairNewObj);
    }

    @Override
    @Transactional
    @TxTransaction
    public int updateCurrencyPair(String currencyPair, Integer status, Integer orderBy, Integer isHome,
                                  Integer isCct, String sysUserId, String ipAdrr) {
        CurrencyPair currencyPairObj = currencyPairMapper.selectByCurrencyPair(currencyPair);
        //防空
        if (currencyPairObj == null) {
            throw new CurrencyException(CurrencyEnums.CURRENCYPAIR_NULL);
        }
        currencyPairObj.setStatus(status);
        currencyPairObj.setOrderBy(orderBy);
        currencyPairObj.setIsHome(isHome);
        currencyPairObj.setIsCct(isCct);
        //更新币币模块的币种
        updateCCTCoin(currencyPair, isCct, sysUserId, ipAdrr);

        //删除缓存
        deleteCacheHomeListAndListList();
        return currencyPairMapper.updateByPrimaryKeySelective(currencyPairObj);
    }

    @Override
    @Transactional
    public int updateCurrencyPaifOfCct(String coinName, String unitName, String status) {
        //查询币对
        CurrencyPair currencyPair = currencyPairMapper.selectByCurrencyPair(getCurrency(coinName, unitName));
        //币对不等于空时
        if (currencyPair != null) {
            //判断币币模块的状态，然后同步行情币对的状态
            if (status.equals(CurrencyConstant.CCT_YES)) {
                currencyPair.setIsCct(CurrencyConstant.CURRENCY_YES);
            }
            if (status.equals(CurrencyConstant.CCT_NO)) {
                currencyPair.setIsCct(CurrencyConstant.CURRENCY_NO);
            }

            //删除缓存
            deleteCacheHomeListAndListList();

            return currencyPairMapper.updateByPrimaryKeySelective(currencyPair);
        }
        return 0;
    }

    /***
     * 根据基本货币和二级货币拼接行情币对字符串
     * @param coinName
     * @param unitName
     * @return
     */
    private String getCurrency(String coinName, String unitName) {
        return MessageFormat.format(CURRENCY_PAIR, coinName, unitName);
    }

    /***
     * 更新币币模块币对
     * @param currencyPair
     * @param status
     * @param sysUserId
     * @param ipAdrr
     */
    private void updateCCTCoin(String currencyPair, int status, String sysUserId, String ipAdrr) {
        String[] coin = currencyPair.split("-");
        if (status == CurrencyConstant.CURRENCY_YES) {
            cctFeign.updateCctCoin(coin[0], coin[1], CurrencyConstant.CCT_YES, sysUserId, ipAdrr);
        }
        if (status == CurrencyConstant.CURRENCY_NO) {
            cctFeign.updateCctCoin(coin[0], coin[1], CurrencyConstant.CCT_NO, sysUserId, ipAdrr);
        }
    }

    /***
     * 删除缓存中的homeList和listList数据
     */
    private void deleteCacheHomeListAndListList() {
        marketCache.deleteList();
        marketCache.deleteHomeList();
    }
}
