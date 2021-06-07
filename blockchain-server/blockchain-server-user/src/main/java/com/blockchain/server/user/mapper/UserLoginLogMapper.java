package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.entity.UserLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 15:28
 * @user WIN10
 */
@Repository
public interface UserLoginLogMapper extends Mapper<UserLoginLog> {

    /**
     * 查询用户登录日志信息
     * @return
     */
    List<UserLoginLogDto> listUserLoginLog(@Param("mobilePhone") String mobilePhone, @Param("realName") String realName);
}
