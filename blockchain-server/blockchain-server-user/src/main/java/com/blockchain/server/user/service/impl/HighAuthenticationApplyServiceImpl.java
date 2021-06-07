package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.HighAuthenticationApply;
import com.blockchain.server.user.mapper.HighAuthenticationApplyMapper;
import com.blockchain.server.user.service.HighAuthenticationApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/8 16:40
 * @user WIN10
 */
@Service
public class HighAuthenticationApplyServiceImpl implements HighAuthenticationApplyService {

    @Autowired
    private HighAuthenticationApplyMapper highAuthenticationApplyMapper;

    /**
     * 查询用户高级审核申请
     * @param userId
     * @return
     */
    @Override
    public HighAuthenticationApply selectHighAuthenticationApplyByUserId(String userId) {
        HighAuthenticationApply example = new HighAuthenticationApply();
        example.setUserId(userId);
        return highAuthenticationApplyMapper.selectOne(example);
    }

    /**
     * 通过状态和用户id查询高级审核申请表
     * @param userId
     * @param status
     * @return
     */
    @Override
    public HighAuthenticationApply selectAuthenticationApplyByUserIdAndStatus(String userId, String status) {
        HighAuthenticationApply example = new HighAuthenticationApply();
        example.setUserId(userId);
        example.setStatus(status);
        return highAuthenticationApplyMapper.selectOne(example);
    }

    @Override
    @Transactional
    public Integer updateAuthenticationApplyStatus(HighAuthenticationApply highAuthenticationApply) {
        return highAuthenticationApplyMapper.updateByPrimaryKeySelective(highAuthenticationApply);
    }

    @Override
    public List<AuthenticationDto> selectAuthenticationList(AuthenticationDto params) {
        return highAuthenticationApplyMapper.selectAuthenticationList(params);
    }

}
