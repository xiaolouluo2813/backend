package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.Version;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface VersionMapper extends Mapper<Version> {

    Version checkVersion(@Param("device") String device);

    Version findNewVersion(@Param("device") String device);

    void deleteVersionById(@Param("id") String id);

    List<Version> listAll(@Param("device") String device);

    Version findVersionById(@Param("id") String id);

    /**
     * 查询app应用版本列表
     *
     * @param version app应用版本信息
     * @return app应用版本集合
     */
    List<Version> selectVersionList(Version version);

}
