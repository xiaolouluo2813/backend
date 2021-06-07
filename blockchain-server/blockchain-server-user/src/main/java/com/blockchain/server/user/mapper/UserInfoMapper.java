package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.UserListDto;
import com.blockchain.server.user.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/4 18:40
 * @user WIN10
 */
@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {

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
    List<UserListDto> listSearchUser(@Param("mobilePhone") String mobilePhone, @Param("email") String email,
                                     @Param("lowAuth") String lowAuth, @Param("highAuth") String highAuth,
                                     @Param("incrCode") Integer incrCode, @Param("startTime") String startTime,
                                     @Param("endTime") String endTime);

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
    List<UserListDto> realNameAudit(@Param("mobilePhone") String mobilePhone, @Param("realName") String realName, @Param("lowAuth") String lowAuth, @Param("highAuth") String highAuth, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("sort") String sort);

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    UserListDto selectUserInfoAndMainByUserId(@Param("userId") String userId);

    /**
     * 查询用户基本认证信息
     *
     * @param userId
     * @return
     */
    UserListDto selectUserAuthenticationInfoByUserId(@Param("userId") String userId);

    /**
     * 查询用户资产信息
     *
     * @param realName
     * @param mobilePhone
     * @param email
     * @return
     */
    List<UserListDto> listSearchUserAsset(@Param("realName") String realName, @Param("mobilePhone") String mobilePhone, @Param("email") String email);
}
