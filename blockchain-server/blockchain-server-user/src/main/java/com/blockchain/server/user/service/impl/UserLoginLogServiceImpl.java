package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.dto.UserLoginLogDto;
import com.blockchain.server.user.entity.UserLoginLog;
import com.blockchain.server.user.mapper.UserLoginLogMapper;
import com.blockchain.server.user.service.UserLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/5 15:27
 * @user WIN10
 */
@Service
public class UserLoginLogServiceImpl implements UserLoginLogService {

    @Autowired
    private UserLoginLogMapper userLoginLogMapper;

    /**
     * 查询用户登录日志
     * @return
     */
    @Override
    public List<UserLoginLogDto> listUserLoginLog(String mobilePhone, String realName) {
        return userLoginLogMapper.listUserLoginLog(mobilePhone, realName);
    }
}
