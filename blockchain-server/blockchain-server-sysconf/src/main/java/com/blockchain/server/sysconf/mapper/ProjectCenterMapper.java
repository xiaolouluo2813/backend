package com.blockchain.server.sysconf.mapper;

import com.blockchain.server.sysconf.dto.ProjectCenterDto;
import com.blockchain.server.sysconf.entity.ProjectCenterInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface ProjectCenterMapper extends Mapper<ProjectCenterInfo> {
    List<ProjectCenterDto> list(@Param("status") String status, @Param("currencyName") String currencyName);
}
