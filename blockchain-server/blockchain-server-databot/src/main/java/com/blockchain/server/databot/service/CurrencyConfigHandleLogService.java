package com.blockchain.server.databot.service;

import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;

import java.util.List;

public interface CurrencyConfigHandleLogService {

    /***
     * 插入更新操作日志
     * @param currencyConfig
     * @return
     */
    int insertUpdateHandleLog(UpdateConfigParamDTO paramDTO, CurrencyConfig currencyConfig, String sysUserId, String ipAddress);

    /***
     * 插入新增操作日志
     * @param currencyConfig
     * @return
     */
    int insertInsertHandleLog(CurrencyConfig currencyConfig, String sysUserId, String ipAddress);

    /***
     * 查询操作日志列表
     * @param currencyPair
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListConfigHandleLogResultDTO> listConfigHandleLog(String currencyPair, String handleType,
                                                           String beginTime, String endTime);


}
