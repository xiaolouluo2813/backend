package com.blockchain.server.user.service.impl;

import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.user.common.constant.UserListConstant;
import com.blockchain.server.user.common.util.CommonUtils;
import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.UserInfo;
import com.blockchain.server.user.mapper.UserInfoMapper;
import com.blockchain.server.user.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/4 18:39
 * @user WIN10
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据需求查询用户列表
     *
     * @param mobilePhone
     * @param email
     * @param lowAuth
     * @param highAuth
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<UserListDto> listSearchUser(String mobilePhone, String email, String lowAuth, String highAuth, String invitationCode, String startTime, String endTime) {
        Integer incrCode = CommonUtils.getUserIncrCodeFromInvitationCode(invitationCode);//解析出用户自增长码
        if (incrCode == null && StringUtils.isNotBlank(invitationCode)) {
            return new ArrayList<UserListDto>();
        }
        List<UserListDto> userListDtos = userInfoMapper.listSearchUser(mobilePhone, email, lowAuth, highAuth, incrCode, startTime, endTime);
        for (UserListDto userListDto : userListDtos) {
            userListDto.setInvitationCode(CommonUtils.generateInvitationCode(userListDto.getIncrCode(), userListDto.getRandomNumber()));
        }
        return userListDtos;
    }

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
    @Override
    public List<UserListDto> realNameAudit(String mobilePhone, String realName, String lowAuth, String highAuth, String startTime, String endTime, String sort) {
        if (UserListConstant.SORT_ASC.equalsIgnoreCase(sort))
            return userInfoMapper.realNameAudit(mobilePhone, realName, lowAuth, highAuth, startTime, endTime, UserListConstant.SORT_ASC_SQL);
        return userInfoMapper.realNameAudit(mobilePhone, realName, lowAuth, highAuth, startTime, endTime, null);
    }

    /**
     * 通过用户id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo selectUserInfoByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        UserInfo example = new UserInfo();
        example.setUserId(userId);
        return userInfoMapper.selectOne(example);
    }

    /**
     * 修改初级审核状态
     *
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public Integer updateUserInfoStatus(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserListDto selectUserInfoAndMainByUserId(String userId) {
        ExceptionPreconditionUtils.notEmpty(userId);
        return userInfoMapper.selectUserInfoAndMainByUserId(userId);
    }

    /**
     * 查询用户基本认证信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserListDto selectUserAuthenticationInfoByUserId(String userId) {
        return userInfoMapper.selectUserAuthenticationInfoByUserId(userId);
    }

    /**
     * 查询用户资产信息
     *
     * @param realName
     * @param mobilePhone
     * @param email
     * @return
     */
    @Override
    public List<UserListDto> listSearchUserAsset(String realName, String mobilePhone, String email) {
        return userInfoMapper.listSearchUserAsset(realName, mobilePhone, email);
    }

}
