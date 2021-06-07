package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.user.common.constant.UserAuthenticationConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exception.UserException;
import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.*;
import com.blockchain.server.user.mapper.UserAuthenticationMapper;
import com.blockchain.server.user.service.*;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author Harvey
 * @date 2019/3/7 10:30
 * @user WIN10
 */
@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    @Autowired
    private UserAuthenticationMapper userAuthenticationMapper;
    @Autowired
    private AuthenticationApplyService authenticationApplyService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthenticationApplyLogService authenticationApplyLogService;
    @Autowired
    private HighAuthenticationApplyService highAuthenticationApplyService;

    @Override
    public String selectHighRemarkByUserId(String userId) {
        AuthenticationApplyLog authenticationApplyLog = authenticationApplyLogService.selectRemarkByUserId(UserAuthenticationConstant.HIGH,userId);
        return authenticationApplyLog!=null?authenticationApplyLog.getRemark():null;
    }

    @Override
    public String selectLowRemarkByUserId(String userId) {
        AuthenticationApplyLog authenticationApplyLog = authenticationApplyLogService.selectRemarkByUserId(UserAuthenticationConstant.LOW,userId);
        return authenticationApplyLog!=null?authenticationApplyLog.getRemark():null;
    }

    /**
     * 查询用户初级审核申请
     *
     * @param userId
     * @return
     */
    @Override
    public AuthenticationApply selectLowAuthByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        AuthenticationApply authenticationApply = authenticationApplyService.selectAuthenticationApplyByUserIdAndStatus(userId,UserAuthenticationConstant.WAITING);
        return authenticationApply;
    }

    /**
     * 查询用户高级审核申请
     *
     * @param userId
     * @return
     */
    @Override
    public HighAuthenticationApply selectHighAuthByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        return highAuthenticationApplyService.selectAuthenticationApplyByUserIdAndStatus(userId,UserAuthenticationConstant.WAITING);
    }

    /**
     * 提交用户初级审核申请
     *
     * @param userId
     * @param applyId
     * @return
     */
    @Override
    @Transactional
    public Integer handleSubmitLowAuthentication(String remark,String userId, String applyId, String status) {
        ExceptionPreconditionUtils.notEmpty(userId);
        ExceptionPreconditionUtils.notEmpty(applyId);
        UserInfo userInfo = userInfoService.selectUserInfoByUserId(userId);
        AuthenticationApply authenticationApply = authenticationApplyService.selectAuthenticationApplyByUserIdAndStatus(userId, UserAuthenticationConstant.WAITING);
        if (userInfo == null) throw new UserException(UserEnums.USER_NOT_EXISTS);
        if (authenticationApply == null) throw new UserException(UserEnums.AUTHENTICATION_APPLY_NOT_EXISTS);
        if (UserAuthenticationConstant.FAILED.equals( status)) {
            // 审核驳回修改数据
            userInfo.setLowAuth(status);
            userInfoService.updateUserInfoStatus(userInfo);
            authenticationApply.setStatus(status);
            authenticationApply.setRemark(remark);
            authenticationApplyService.updateAuthenticationApplyStatus(authenticationApply);
        } else {
            // 审核通过修改数据并插入审核记录表
            userInfo.setLowAuth(status);
            userInfoService.updateUserInfoStatus(userInfo);
            authenticationApply.setStatus(status);
            authenticationApplyService.updateAuthenticationApplyStatus(authenticationApply);

            UserAuthentication userAuthentication = new UserAuthentication();
            Date date = new Date();
            userAuthentication.setId(UUID.randomUUID().toString());
            userAuthentication.setUserId(userId);
            userAuthentication.setCreateTime(date);
            userAuthentication.setType(authenticationApply.getType());
            userAuthentication.setIdNumber(authenticationApply.getIdNumber());
            userAuthentication.setRealName(authenticationApply.getRealName());
            userAuthentication.setFileUrl1(authenticationApply.getFileUrl1());
            userAuthentication.setFileUrl2(authenticationApply.getFileUrl2());
            userAuthentication.setModifyTime(date);
            userAuthenticationMapper.insertSelective(userAuthentication);
        }
        AuthenticationApplyLog authenticationApplyLog = new AuthenticationApplyLog();
        authenticationApplyLog.setId(UUID.randomUUID().toString());
        authenticationApplyLog.setUserId(userId);
        authenticationApplyLog.setApplyId(authenticationApply.getId());
        authenticationApplyLog.setType(UserAuthenticationConstant.LOW);
        authenticationApplyLog.setSysUserId(SecurityUtils.getUserId());
        authenticationApplyLog.setIpAddress(HttpRequestUtil.getIpAddr());
        authenticationApplyLog.setApplyResult(status);
        if (UserAuthenticationConstant.FAILED.equals( status)){
            authenticationApplyLog.setRemark(remark);
        }
        authenticationApplyLog.setCreateTime(new Date());
        authenticationApplyLogService.insert(authenticationApplyLog);
        return 1;

    }

    /**
     * 提交用户高级审核申请
     *
     * @param userId
     * @param applyId
     * @param status
     * @return
     */
    @Override
    @Transactional
    public Integer handleSubmitHighAuthentication(String remark,String userId, String applyId, String status) {
        ExceptionPreconditionUtils.notEmpty(userId);
        ExceptionPreconditionUtils.notEmpty(applyId);
        UserInfo userInfo = userInfoService.selectUserInfoByUserId(userId);
        HighAuthenticationApply highAuthenticationApply = highAuthenticationApplyService.selectAuthenticationApplyByUserIdAndStatus(userId, UserAuthenticationConstant.WAITING);
        if (userInfo == null) throw new UserException(UserEnums.USER_NOT_EXISTS);
        if (highAuthenticationApply == null) throw new UserException(UserEnums.AUTHENTICATION_APPLY_NOT_EXISTS);
        if (UserAuthenticationConstant.FAILED.equals( status)) {
            // 审核驳回修改数据
            userInfo.setHighAuth(status);
            userInfoService.updateUserInfoStatus(userInfo);
            highAuthenticationApply.setStatus(status);
            highAuthenticationApply.setRemark(remark);
            highAuthenticationApplyService.updateAuthenticationApplyStatus(highAuthenticationApply);
        } else {
            UserAuthentication example = new UserAuthentication();
            example.setUserId(userId);
            UserAuthentication userAuthentication = userAuthenticationMapper.selectOne(example);
            // 审核通过修改数据并插入审核记录表
            userInfo.setHighAuth(status);
            userInfoService.updateUserInfoStatus(userInfo);
            highAuthenticationApply.setStatus(status);
            highAuthenticationApplyService.updateAuthenticationApplyStatus(highAuthenticationApply);

            userAuthentication.setFileUrl4(highAuthenticationApply.getFileUrl());
            userAuthentication.setModifyTime(new Date());
            userAuthenticationMapper.updateByPrimaryKeySelective(userAuthentication);
        }
        AuthenticationApplyLog authenticationApplyLog = new AuthenticationApplyLog();
        authenticationApplyLog.setId(UUID.randomUUID().toString());
        authenticationApplyLog.setUserId(userId);
        authenticationApplyLog.setApplyId(highAuthenticationApply.getId());
        authenticationApplyLog.setType(UserAuthenticationConstant.HIGH);
        authenticationApplyLog.setSysUserId(SecurityUtils.getUserId());
        authenticationApplyLog.setIpAddress(HttpRequestUtil.getIpAddr());
        authenticationApplyLog.setApplyResult(status);
        if (UserAuthenticationConstant.FAILED.equals( status)){
            authenticationApplyLog.setRemark(remark);
        }
        authenticationApplyLog.setCreateTime(new Date());
        authenticationApplyLogService.insert(authenticationApplyLog);
        return 1;
    }

    /**
     * 查询用户认证信息
     * @param userId
     * @return
     */
    @Override
    public UserAuthentication selectUserAuthenticationByUserId(String userId) {
        UserAuthentication example = new UserAuthentication();
        example.setUserId(userId);
        return userAuthenticationMapper.selectOne(example);
    }

    /**
     * 查询用户基本认证信息
     * @param userId
     * @return
     */
    @Override
    public UserListDto selectUserAuthenticationInfoByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        return userInfoService.selectUserAuthenticationInfoByUserId(userId);
    }

    /**
     * 查询用户身份证号码是否存在
     * @param identityCode
     * @return
     */
    @Override
    public Integer selectIdentityCode(String identityCode) {
        return userAuthenticationMapper.selectCountByIdentityCode(identityCode);
    }

    /**
     * 修改用户身份证号码
     * @param userId
     * @param identityCode
     * @param date
     * @return
     */
    @Override
    public Integer updateIdentityCodeByUserId(String userId, String identityCode, Date date) {
        ExceptionPreconditionUtils.notEmpty(userId);
        ExceptionPreconditionUtils.notEmpty(identityCode);
        return userAuthenticationMapper.updateIdentityCode(userId, identityCode, date);
    }

}
