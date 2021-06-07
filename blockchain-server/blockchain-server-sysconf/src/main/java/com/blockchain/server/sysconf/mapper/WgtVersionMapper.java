package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.WgtVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface WgtVersionMapper extends Mapper<WgtVersion> {

    WgtVersion findNewWgtVersion();

    void deleteWgtVersionById(@Param("id") String id);

    List<WgtVersion> listAll();

    WgtVersion findWgtVersionById(@Param("id") String id);

    /**
     * 查询app应用补丁版本列表
     *
     * @param wgtVersion app应用补丁版本信息
     * @return app应用版本补丁集合
     */
    List<WgtVersion> selectWgtVersionList(WgtVersion wgtVersion);

}
