package com.blockchain.server.user.mapper;

import com.blockchain.server.user.dto.AuthenticationDto;
import com.blockchain.server.user.entity.HighAuthenticationApply;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author Harvey
 * @date 2019/3/8 16:40
 * @user WIN10
 */
public interface HighAuthenticationApplyMapper extends Mapper<HighAuthenticationApply> {

    List<AuthenticationDto> selectAuthenticationList(@Param("params") AuthenticationDto params);
}
