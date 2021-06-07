package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.Automaticdata;

import java.util.List;

public interface AutomaticdataService {

    /***
     * 插入自动生成盘口数据规则
     * @param param
     */
    int insertAutomaticdata(Automaticdata param);

    /***
     * 查询所有盘口数据规则
     * @return
     */
    List<Automaticdata> listAll();

    /***
     * 删除配置信息
     * @param id
     * @return
     */
    int deleteAutomaticdata(String id);

    /***
     * 更新配置信息
     * @param param
     * @return
     */
    int updataAutomaticdata(Automaticdata param);
}
