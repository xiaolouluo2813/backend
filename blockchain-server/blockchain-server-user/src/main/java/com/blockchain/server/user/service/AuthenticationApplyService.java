package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.AuthenticationApply;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/7 11:09
 * @user WIN10
 */
public interface AuthenticationApplyService {

    /**
     * 查询用户初级审核申请
     * @param userId
     * @return
     */
    AuthenticationApply selectAuthenticationApplyByUserId(String userId);

    /**
     * 查询用户状态初级申请
     * @param userId
     * @param status
     * @return
     */
    AuthenticationApply selectAuthenticationApplyByUserIdAndStatus(String userId, String status);

    /**
     * 修改用户初级审核状态
     * @param authenticationApply
     * @return
     */
    Integer updateAuthenticationApplyStatus(AuthenticationApply authenticationApply);

    /**
     * @Description: 审核记录列表
     * @Param: [params]
     * @return: com.blockchain.common.base.dto.ResultDTO
     * @Author: Liu.sd
     * @Date: 2019/7/16
     */
    List<AuthenticationDto> selectAuthenticationList(AuthenticationDto params);
}
