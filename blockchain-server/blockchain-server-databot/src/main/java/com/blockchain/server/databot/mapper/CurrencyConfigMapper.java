package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.dto.currencyconfig.ListConfigResultDTO;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * CurrencyConfigMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-03 11:37:01
 */
@Repository
public interface CurrencyConfigMapper extends Mapper<CurrencyConfig> {

    /***
     * 根据币对查询配置信息
     * @param currencyPair
     * @return
     */
    CurrencyConfig getConfigByCurrencyPair(@Param("currencyPair") String currencyPair);

    /***
     * 查询所有币对配置信息
     * @param currencyPair
     * @param status
     * @return
     */
    List<ListConfigResultDTO> listConfig(@Param("currencyPair") String currencyPair, @Param("status") String status);

    /***
     * 更新配置信息
     * @param paramDTO
     * @return
     */
    int updateConfig(@Param("paramDTO") UpdateConfigParamDTO paramDTO, @Param("modifyTime") Date modifyTime);
}