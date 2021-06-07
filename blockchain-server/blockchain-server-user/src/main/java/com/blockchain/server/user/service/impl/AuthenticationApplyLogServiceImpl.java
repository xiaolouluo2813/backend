package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.entity.AuthenticationApplyLog;
import com.blockchain.server.user.mapper.AuthenticationApplyLogMapper;
import com.blockchain.server.user.service.AuthenticationApplyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationApplyLogServiceImpl implements AuthenticationApplyLogService {


    @Autowired
    private AuthenticationApplyLogMapper authenticationApplyLogMapper;


    @Override
    public Integer insert(AuthenticationApplyLog authenticationApplyLog) {
        return authenticationApplyLogMapper.insert(authenticationApplyLog);
    }

    @Override
    public AuthenticationApplyLog selectRemarkByUserId(String type, String userId) {
        return authenticationApplyLogMapper.selectRemarkByUserId(type,userId);
    }
}
