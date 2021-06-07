package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constant.UserLogConstant;
import com.blockchain.server.user.common.enums.UserEnums;
import com.blockchain.server.user.common.exception.UserException;
import com.blockchain.server.user.entity.UserAuthentication;
import com.blockchain.server.user.entity.UserLog;
import com.blockchain.server.user.mapper.UserMainMapper;
import com.blockchain.server.user.service.UserAuthenticationService;
import com.blockchain.server.user.service.UserLogService;
import com.blockchain.server.user.service.UserMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Harvey
 * @date 2019/3/9 12:07
 * @user WIN10
 */
@Service
public class UserMainServiceImpl implements UserMainService {

    @Autowired
    private UserMainMapper userMainMapper;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private UserAuthenticationService authenticationService;

    /**
     * 查询单个用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserBaseInfoDTO selectUserInfo(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        UserBaseInfoDTO userInfoDto = userMainMapper.selectUserInfoByUserId(userId);
        if (userInfoDto == null) throw new UserException(UserEnums.USER_NOT_EXISTS);
        return userInfoDto;
    }

    /**
     * 查询多个用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public Map<String, UserBaseInfoDTO> listUserInfo(Set<String> userIds) {
        ExceptionPreconditionUtils.notEmpty(userIds);
        Map<String, UserBaseInfoDTO> map = new HashMap<>();
        for (String userId : userIds) {
            UserBaseInfoDTO userInfoDto = userMainMapper.selectUserInfoByUserId(userId);
            if (userInfoDto == null) continue;
            map.put(userId, userInfoDto);
        }
        return map;
    }

    @Override
    public UserBaseInfoDTO selectUserInfoByUserName(String userName) {
        return userMainMapper.selectUserInfoByUserName(userName);
    }

    /**
     * 修改用户手机
     * @param userId
     * @param mobilePhone
     * @return
     */
    @Override
    @Transactional
    public Integer updateMobilePhone(String userId, String mobilePhone) {
        int mobilePhoneRow = userMainMapper.selectMobilePhone(mobilePhone);
        if (mobilePhoneRow != 0) throw new UserException(UserEnums.USER_MOBILE_PHONE_EXISTS);
        userLogService.insertLog(userId, UserLogConstant.USER_LOG_UPDATE_MPBILE_PHONE);
        int row = userMainMapper.updateMobilePhoneByUserId(userId, mobilePhone, new Date());
        if (row != 1) throw new UserException(UserEnums.USER_MOBILE_PHONE_UPDATE_ERROR);
        return 1;
    }

    /**
     * 修改用户身份证号码
     * @param userId
     * @param identityCode
     * @return
     */
    @Override
    public Integer updateIdentityCode(String userId, String identityCode) {
        int identityCodeRow = authenticationService.selectIdentityCode(identityCode);
        if (identityCodeRow != 0) throw new UserException(UserEnums.USER_IDENTITY_CODE_EXISTS);
        userLogService.insertLog(userId, UserLogConstant.USER_LOG_UPDATE_IDENTITY_CODE);
        int row = authenticationService.updateIdentityCodeByUserId(userId, identityCode, new Date());
        if (row != 1) throw new UserException(UserEnums.USER_IDENTITY_CODE_UPDATE_ERROR);
        return 1;
    }
}
