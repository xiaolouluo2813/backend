package com.blockchain.server.user.mapper;

import com.blockchain.server.user.entity.UserAuthentication;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/7 10:30
 * @user WIN10
 */
@Repository
public interface UserAuthenticationMapper extends Mapper<UserAuthentication> {

    /**
     * 查询用户身份证号码是否存在
     * @param identityCode
     * @return
     */
    Integer selectCountByIdentityCode(@Param("identityCode") String identityCode);

    /**
     * 修改用户身份证号码
     * @param userId
     * @param identityCode
     * @param date
     * @return
     */
    Integer updateIdentityCode(@Param("userId") String userId, @Param("identityCode") String identityCode, @Param("date") Date date);
}
