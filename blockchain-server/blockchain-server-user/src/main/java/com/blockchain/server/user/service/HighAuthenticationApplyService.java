package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.HighAuthenticationApply;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/8 16:40
 * @user WIN10
 */
public interface HighAuthenticationApplyService {
    /**
     * 查询用户高级审核申请
     * @param userId
     * @return
     */
    HighAuthenticationApply selectHighAuthenticationApplyByUserId(String userId);

    /**
     * 通过状态和用户id查询高级审核申请表
     * @param userId
     * @param status
     * @return
     */
    HighAuthenticationApply selectAuthenticationApplyByUserIdAndStatus(String userId, String status);

    /**
     * 修改用户高级审核申请表状态
     * @param highAuthenticationApply
     * @return
     */
    Integer updateAuthenticationApplyStatus(HighAuthenticationApply highAuthenticationApply);

    List<AuthenticationDto> selectAuthenticationList(AuthenticationDto params);
}
