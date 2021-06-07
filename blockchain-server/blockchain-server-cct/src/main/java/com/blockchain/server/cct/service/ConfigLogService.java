package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.ConfigLog;

import java.util.List;

public interface ConfigLogService {

    /***
     * 插入配置修改日志
     *
     * @param log
     * @return
     */
    int insertConfigLog(ConfigLog log);

    /***
     * 查询所有配置修改日志
     * @return
     */
    List<ConfigLog> listConfigLog();
}
