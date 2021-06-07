package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.entity.UserLoginLog;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 15:27
 * @user WIN10
 */
public interface UserLoginLogService {

    /**
     * 查询用户登录日志
     * @return
     */
    List<UserLoginLogDto> listUserLoginLog(String mobilePhone, String realName);
}
