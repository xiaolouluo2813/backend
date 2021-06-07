package com.blockchain.server.user.service;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;

import java.util.Map;
import java.util.Set;


/**
 * @author Harvey
 * @date 2019/3/9 12:06
 * @user WIN10
 */
public interface UserMainService {

    /**
     * 查询单个用户信息
     *
     * @param userId
     * @return
     */
    UserBaseInfoDTO selectUserInfo(String userId);

    /**
     * 查询多个用户信息
     * <p>
     * 根据id
     *
     * @param userIds
     * @return
     */
    Map<String, UserBaseInfoDTO> listUserInfo(Set<String> userIds);

    /**
     * 通过手机查询用户信息
     * @param userName
     * @return
     */
    UserBaseInfoDTO selectUserInfoByUserName(String userName);

    /**
     * 修改用户手机
     * @param userId
     * @param mobilePhone
     */
    Integer updateMobilePhone(String userId, String mobilePhone);

    /**
     * 修改用户身份证号码
     * @param userId
     * @param identityCode
     * @return
     */
    Integer updateIdentityCode(String userId, String identityCode);
}
