package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.AuthenticationApply;
import com.blockchain.server.user.mapper.AuthenticationApplyMapper;
import com.blockchain.server.user.service.AuthenticationApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/7 11:09
 * @user WIN10
 */
@Service
public class AuthenticationApplyServiceImpl implements AuthenticationApplyService {

    @Autowired
    private AuthenticationApplyMapper authenticationApplyMapper;

    @Override
    public AuthenticationApply selectAuthenticationApplyByUserId(String userId) {
        AuthenticationApply example = new AuthenticationApply();
        example.setUserId(userId);
        return authenticationApplyMapper.selectOne(example);
    }

    /**
     * 查询用户状态初级申请
     * @param userId
     * @param status
     * @return
     */
    @Override
    public AuthenticationApply selectAuthenticationApplyByUserIdAndStatus(String userId, String status) {
        AuthenticationApply example = new AuthenticationApply();
        example.setUserId(userId);
        example.setStatus(status);
        return authenticationApplyMapper.selectOne(example);
    }

    /**
     * 修改用户初级审核状态
     * @param authenticationApply
     * @return
     */
    @Override
    @Transactional
    public Integer updateAuthenticationApplyStatus(AuthenticationApply authenticationApply) {
        return authenticationApplyMapper.updateByPrimaryKeySelective(authenticationApply);
    }

    @Override
    public List<AuthenticationDto> selectAuthenticationList(AuthenticationDto params) {
        return authenticationApplyMapper.selectAuthenticationList(params);
    }

}
