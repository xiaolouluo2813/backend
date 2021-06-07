package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.config.ListConfigResultDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ConfigService {

    /***
     * 查询配置信息列表
     * @return
     */
    List<ListConfigResultDTO> listConfig();

    /***
     * 根据key查询配置信息列表
     * @return
     */
    @Deprecated
    List<ListConfigResultDTO> listConfig(String[] dataKeys);

    /***
     * 更新配置信息
     * @param id
     * @param dataValue
     * @param status
     * @return
     */
    int updateConfig(String sysUserId, String ipAddress,
                     String id, String dataValue, String status);

    /***
     * 查询市商保证金扣除的代币
     * @return
     */
    String selectMarketFreezeCoin();

    /***
     * 查询市商保证金数量
     * @return
     */
    BigDecimal selectMarketFreezeAmount();
}
