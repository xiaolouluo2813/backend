package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.AuthenticationApplyDto;
import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.AuthenticationApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/7 11:10
 * @user WIN10
 */
@Repository
public interface AuthenticationApplyMapper extends Mapper<AuthenticationApply> {

    /**
     * 判断用户是否认证接口
     * @param userId
     * @return
     */
    String judgeAuthentication(@Param("userId") String userId);

    List<AuthenticationDto> selectAuthenticationList(@Param("params") AuthenticationDto params);
}
