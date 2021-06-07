package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.entity.Version;

import java.util.List;
import java.util.Map;

public interface VersionService {
    /**
     * 修改app版本部分内容
     *
     * @param id
     * @param version
     * @param appUrl
     * @param remark
     * @param compel
     * @param device
     */
    int updateVersion(String id, String version, String appUrl, String remark, Integer compel, String device);

    /**
     * 根据系统类型查询最新app版本信息
     *
     * @param device 设备型号
     * @return
     */
    Version findNewVersion(String device);

    /**
     * 查询所有app版本信息列表
     *
     * @param device
     * @return
     */
    List<Version> listAll(String device);

    /**
     * 查询所有系统最新的版本信息
     *
     * @return
     */
    Map<String, Version> findNewVersionAll();

    /**
     * 根据id删除旧版本
     *
     * @param id
     */
    void deleteVersionById(String id);

    /**
     * 新增版本信息
     *
     * @param version
     * @param appUrl
     * @param remark
     * @param compel
     * @param device
     */
    int saveVersion(String version, String appUrl, String remark, Integer compel, String device);

    /**
     * 根据id查询版本信息
     *
     * @param id
     * @return
     */
    Version findVersionById(String id);

    /**
     * 查询app应用版本列表
     *
     * @param version app应用版本信息
     * @return app应用版本集合
     */
    public List<Version> selectVersionList(Version version);


}
