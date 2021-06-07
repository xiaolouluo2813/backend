package com.blockchain.server.system.mapper;

import com.blockchain.server.system.dto.SystemMenuResultDto;
import com.blockchain.server.system.dto.UserMenuDto;
import com.blockchain.server.system.entity.SystemMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Repository
public interface SystemMenuMapper extends Mapper<SystemMenu> {

    List<SystemMenuResultDto> systeMenuListByStatusAndTypeAndPid(@Param("status") String status, @Param("type") String type, @Param("pid") String pid);

    List<SystemMenuResultDto> roleMenuList(@Param("roleIds") List<String> roleIds);

    List<UserMenuDto> userMenuListAll(@Param("userId") String userId);

    String[] menuListByRoleId(@Param("id")String id);
}