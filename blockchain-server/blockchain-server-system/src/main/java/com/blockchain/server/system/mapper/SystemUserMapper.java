package com.blockchain.server.system.mapper;

import com.blockchain.server.system.dto.SystemUserResultDto;
import com.blockchain.server.system.entity.SystemUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author
 * @create 2018-11-08 21:00
 */
@Repository
public interface SystemUserMapper extends Mapper<SystemUser> {
    List<SystemUserResultDto> systemUserList(@Param("status") String status,@Param("name") String name,@Param("phone") String phone);
}
