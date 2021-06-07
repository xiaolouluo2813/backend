package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.coin.ListCoinResultDTO;
import com.blockchain.server.otc.entity.Coin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CoinMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface CoinMapper extends Mapper<Coin> {

    /***
     * 根据币种、单位查询币种信息
     * @param coinName
     * @return
     */
    Coin selectByCoinAndUnit(@Param("coinName") String coinName,
                             @Param("unitName") String unitName);

    /***
     * 查询币种列表
     * @param coinName
     * @param unitName
     * @return
     */
    List<ListCoinResultDTO> listCoin(@Param("coinName") String coinName, @Param("unitName") String unitName);
}