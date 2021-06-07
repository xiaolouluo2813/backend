package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.Config;

import java.util.List;

public interface ConfigService {
    /***
     * 更新配置
     *
     * @param sysUserId
     * @param ipAddr
     * @param key
     * @param val
     * @param status
     * @return
     */
    int updateConfig(String sysUserId, String ipAddr, String tag, String key, String val, String status);

    /***
     * 更新佣金发放配置
     * @param sysUserId
     * @param ipAddr
     * @param type
     * @param day
     * @param hour
     * @param status
     * @return
     */
    int updateCommissionIssueTime(String sysUserId, String ipAddr, String type, Integer day, Integer hour, String status);

    /***
     * 查询配置信息列表
     * @return
     */
    List<Config> listConfig();

    /***
     * 根据key查询配置信息
     * @param key
     * @return
     */
    Config selectByKey(String key);

    /***
     * 打开佣金发放定时器
     * @param corn
     */
    void openCommissionSchedule(String corn);

    /***
     * 关闭佣金发放定时器
     */
    void closeCommissionSchedule();
}
