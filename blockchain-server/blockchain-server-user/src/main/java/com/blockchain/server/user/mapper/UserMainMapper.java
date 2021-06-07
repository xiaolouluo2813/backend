package com.blockchain.server.user.mapper;

import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.user.entity.UserMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/9 12:44
 * @user WIN10
 */
@Repository
public interface UserMainMapper extends Mapper<UserMain> {

    /**
     * 查询单个用户信息
     * <p>
     * 根据id
     *
     * @param userId
     * @return
     */
    UserBaseInfoDTO selectUserInfoByUserId(@Param("userId") String userId);

    /**
     * 通过手机查询用户信息
     * @param userName
     * @return
     */
    UserBaseInfoDTO selectUserInfoByUserName(@Param("userName") String userName);

    /**
     * 查询用户号码是否存在
     * @param mobilePhone
     * @return
     */
    Integer selectMobilePhone(@Param("mobilePhone") String mobilePhone);

    /**
     * 修改用户号码
     * @param userId
     * @param mobilePhone
     * @return
     */
    Integer updateMobilePhoneByUserId(@Param("userId") String userId, @Param("mobilePhone") String mobilePhone, @Param("date") Date date);

}
