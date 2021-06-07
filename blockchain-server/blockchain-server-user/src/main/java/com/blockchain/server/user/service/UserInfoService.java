package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.UserInfo;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/4 18:39
 * @user WIN10
 */
public interface UserInfoService {

    /**
     * 根据需求查询用户列表
     *
     * @param mobilePhone
     * @param email
     * @param lowAuth
     * @param highAuth
     * @param invitationCode
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserListDto> listSearchUser(String mobilePhone, String email, String lowAuth, String highAuth, String invitationCode, String startTime, String endTime);

    /**
     * 实名审核
     *
     * @param mobilePhone
     * @param realName
     * @param lowAuth
     * @param highAuth
     * @param startTime
     * @param endTime
     * @return
     */
    List<UserListDto> realNameAudit(String mobilePhone, String realName, String lowAuth, String highAuth, String startTime, String endTime, String sort);

    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return
     */
    UserInfo selectUserInfoByUserId(String userId);

    /**
     * 修改初级审核状态
     *
     * @param userInfo
     * @return
     */
    Integer updateUserInfoStatus(UserInfo userInfo);

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    UserListDto selectUserInfoAndMainByUserId(String userId);

    /**
     * 查询用户基本认证信息
     *
     * @param userId
     * @return
     */
    UserListDto selectUserAuthenticationInfoByUserId(String userId);

    /**
     * 查询用户资产信息
     *
     * @param realName
     * @param mobilePhone
     * @param email
     * @return
     */
    List<UserListDto> listSearchUserAsset(String realName, String mobilePhone, String email);
}
