package com.blockchain.server.currency.mapper;

import com.blockchain.server.currency.entity.Currency;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CurrencyMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-14 15:06:53
 */
@Repository
public interface CurrencyMapper extends Mapper<Currency> {

    /***
     * 查询所有币种
     * @param coinName
     * @param status
     * @return
     */
    List<Currency> listCurrency(@Param("coinName") String coinName, @Param("status") Integer status);

    /***
     * 根据币种名查询币种信息
     * @param coinName
     * @return
     */
    Currency selectByCoinName(@Param("coinName") String coinName);
}