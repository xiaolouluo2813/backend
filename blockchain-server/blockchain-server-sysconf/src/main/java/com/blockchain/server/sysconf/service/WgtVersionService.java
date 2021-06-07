package com.blockchain.server.sysconf.service;


import com.blockchain.server.sysconf.entity.WgtVersion;

import java.util.List;

public interface WgtVersionService {
    /**
     * 修改app版本部分内容
     * @param id
     * @param wgtVersion
     * @param wgtUrl
     * @param remark
     */
    int updatewgtVersion(String id, String wgtVersion, String wgtUrl, String remark);

    /**
     * 根据查询最新补丁版本信息
     * @return
     */
    WgtVersion findNewWgtVersion();
    /**
     * 查询所有版本版本信息列表
     * @return
     */
    List<WgtVersion> listAll();

    /**
     * 根据id删除旧版本
     * @param id
     */
    void deleteWgtVersionById(String id);

    /**
     * 新增版本信息
     * @param wgtVersion
     * @param wgtUrl
     * @param remark
     */
    int saveWgtVersion(String wgtVersion, String wgtUrl, String remark);

    /**
     * 根据id查询版本信息
     * @param id
     * @return
     */
    WgtVersion findWgtVersionById(String id);

    /**
     * 查询app应用补丁版本列表
     *
     * @param wgtVersion app应用补丁版本信息
     * @return app应用补丁版本集合
     */
    public List<WgtVersion> selectVersionList(WgtVersion wgtVersion);

}
