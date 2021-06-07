package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.entity.ProjectCenterClassify;
import com.blockchain.server.sysconf.entity.ProjectCenterReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ProjectCenterReportMapper extends Mapper<ProjectCenterReport> {
    List<ProjectCenterReport> list(@Param("status") String status, @Param("projectId") String projectId);
}
