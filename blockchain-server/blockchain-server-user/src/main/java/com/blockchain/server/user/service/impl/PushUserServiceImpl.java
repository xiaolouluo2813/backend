package com.blockchain.server.user.service.impl;

import com.blockchain.server.user.entity.PushUser;
import com.blockchain.server.user.mapper.PushUserMapper;
import com.blockchain.server.user.service.PushUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushUserServiceImpl implements PushUserService {
    @Autowired
    private PushUserMapper userMapper;

    @Override
    public PushUser selectByUserId(String userId) {
        return userMapper.selectByUserId(userId);
    }

    @Override
    public PushUser selectByClientId(String clientId) {
        return userMapper.selectByClientId(clientId);
    }
}
