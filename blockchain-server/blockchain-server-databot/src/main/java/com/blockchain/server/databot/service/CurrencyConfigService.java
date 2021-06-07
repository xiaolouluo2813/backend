package com.blockchain.server.databot.service;

import com.blockchain.server.databot.dto.currencyconfig.InsertConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfig.ListConfigResultDTO;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;

import java.util.List;

public interface CurrencyConfigService {

    /***
     * 查询所有币对配置信息
     * @param currencyPair
     * @param status
     * @return
     */
    List<ListConfigResultDTO> listConfig(String currencyPair, String status);

    /***
     * 根据币对查询配置信息
     * @param currencyPair
     * @return
     */
    CurrencyConfig getConfigByCurrencyPair(String currencyPair);

    /***
     * 新增币对配置信息
     * @param paramDTO
     * @param sysUserId
     * @param ipAddress
     * @return
     */
    int insertConfig(InsertConfigParamDTO paramDTO, String sysUserId, String ipAddress);

    /***
     * 更新币对配置信息
     * @param paramDTO
     * @param sysUserId
     * @param ipAddress
     * @return
     */
    int updateConfig(UpdateConfigParamDTO paramDTO, String sysUserId, String ipAddress);
}
