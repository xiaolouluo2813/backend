package com.blockchain.server.system.mapper;

import com.blockchain.server.system.dto.SystemRoleResultDto;
import com.blockchain.server.system.entity.SystemRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Repository
public interface SystemRoleMapper extends Mapper<SystemRole> {
    List<SystemRoleResultDto> systemRoleList(@Param("status") String status,@Param("name") String name);

    List<SystemRoleResultDto> userRoleList(@Param("status") String status,@Param("id")String id);
}