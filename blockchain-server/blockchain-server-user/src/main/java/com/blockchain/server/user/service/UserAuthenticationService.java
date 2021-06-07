package com.blockchain.server.user.service;

import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.AuthenticationApply;
import com.blockchain.server.user.entity.HighAuthenticationApply;
import com.blockchain.server.user.entity.UserAuthentication;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/7 10:29
 * @user WIN10
 */
public interface UserAuthenticationService {

    /**
     * 查询高级未认证原因
     * @param userId
     * @return
     */
    String selectHighRemarkByUserId(String userId);
    /**
     * 查询初级未认证原因
     * @param userId
     * @return
     */
    String selectLowRemarkByUserId(String userId);
    /**
     * 查询用户初级审核申请
     * @param userId
     * @return
     */
    AuthenticationApply selectLowAuthByUserId(String userId);

    /**
     * 查询用户高级审核申请
     * @param userId
     * @return
     */
    HighAuthenticationApply selectHighAuthByUserId(String userId);

    /**
     * 提交用户初级审核申请
     * @param userId
     * @param applyId
     */
    Integer handleSubmitLowAuthentication(String remark,String userId, String applyId, String status);

    /**
     * 提交用户高级审核申请
     * @param userId
     * @param applyId
     * @param status
     * @return
     */
    Integer handleSubmitHighAuthentication(String remark,String userId, String applyId, String status);

    /**
     * 查询用户认证信息
     * @param userId
     * @return
     */
    UserAuthentication selectUserAuthenticationByUserId(String userId);

    /**
     * 查询用户基本认证信息
     * @param userId
     * @return
     */
    UserListDto selectUserAuthenticationInfoByUserId(String userId);

    /**
     * 查询用户身份证号码是否存在
     * @param identityCode
     * @return
     */
    Integer selectIdentityCode(String identityCode);

    /**
     * 修改用户身份证号码
     * @param userId
     * @param identityCode
     * @param date
     * @return
     */
    Integer updateIdentityCodeByUserId(String userId, String identityCode, Date date);
}
