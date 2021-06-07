package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.Coin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppCctCoinMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface CoinMapper extends Mapper<Coin> {

    /***
     * 根据币对、状态查询币种信息
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    Coin selectByCoinUnitAndStatus(@Param("coinName") String coinName, @Param("unitName") String unitName, @Param("status") String status);

    /***
     * 查询交易对列表
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    List<Coin> listCoin(@Param("coinName") String coinName, @Param("unitName") String unitName, @Param("status") String status);

    /***
     * 根据二级货币查询，并且以二级货币分组
     * @param unitName
     * @return
     */
    Coin selectByUnitAndGroupByUnit(@Param("unitName") String unitName);
}