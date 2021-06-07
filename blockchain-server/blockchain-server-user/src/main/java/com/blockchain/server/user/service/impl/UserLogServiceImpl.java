package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.user.entity.UserLog;
import com.blockchain.server.user.mapper.UserLogMapper;
import com.blockchain.server.user.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author Harvey Luo
 * @date 2019/6/16 13:55
 */
@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper logMapper;

    /**
     * 修改手机号码添加方法
     * @param type
     * @return
     */
    @Override
    public Integer insertLog(String userId, String type) {
        UserLog userLog = new UserLog();
        userLog.setId(UUID.randomUUID().toString());
        userLog.setContent(type);
        userLog.setCreateTime(new Date());
        userLog.setUserId(userId);
        userLog.setIpAddress(HttpRequestUtil.getIpAddr());
        userLog.setSysUserId(SecurityUtils.getUserId());
        return logMapper.insertSelective(userLog);
    }
}
