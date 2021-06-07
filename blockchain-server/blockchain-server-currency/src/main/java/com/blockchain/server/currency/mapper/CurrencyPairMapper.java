package com.blockchain.server.currency.mapper;

import com.blockchain.server.currency.entity.CurrencyPair;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CurrencyPairMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-14 15:06:53
 */
@Repository
public interface CurrencyPairMapper extends Mapper<CurrencyPair> {

    /***
     * 查询行情
     * @param currencyPair
     * @param status
     * @return
     */
    List<CurrencyPair> listCurrencyPair(@Param("currencyPair") String currencyPair, @Param("status") Integer status);

    /***
     * 根据币对查询行情
     * @param currencyPair
     * @return
     */
    CurrencyPair selectByCurrencyPair(@Param("currencyPair") String currencyPair);
}