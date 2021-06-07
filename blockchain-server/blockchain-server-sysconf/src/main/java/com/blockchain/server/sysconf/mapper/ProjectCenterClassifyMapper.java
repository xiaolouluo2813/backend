package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProjectCenterClassifyMapper extends Mapper<ProjectCenterClassify> {
    List<ProjectCenterClassify> list(@Param("status") String status, @Param("name") String name);
}
