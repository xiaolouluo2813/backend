package com.blockchain.server.system.service;

import com.blockchain.server.system.dto.SystemMenuAddDto;
import com.blockchain.server.system.dto.SystemMenuResultDto;
import com.blockchain.server.system.dto.UserMenuDto;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:49
 **/
public interface SystemMenuService {
    
    /** 
    * @Description:  新增菜单
    * @Param: [systemMenuAddDto] 
    * @return: int 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    int insert(SystemMenuAddDto systemMenuAddDto);

    /** 
    * @Description: 标识唯一验证 
    * @Param: [code] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void codeCheck(String code);

    /** 
    * @Description: 菜单列表 
    * @Param: [status] 
    * @return: java.util.List<com.blockchain.server.system.dto.SystemMenuResultDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    List<SystemMenuResultDto> systemMenuList(String status);

    /** 
    * @Description: 角色菜单列表
    * @Param: [roleIds] 
    * @return: java.util.List<com.blockchain.server.system.dto.SystemMenuResultDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    List<SystemMenuResultDto> roleMenuList(List<String> roleIds);

    /** 
    * @Description: 用户菜单列表 
    * @Param: [status, userId] 
    * @return: java.util.List<com.blockchain.server.system.dto.SystemMenuResultDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    List<SystemMenuResultDto> userMenuList(String status, String userId);

    /** 
    * @Description: 用户菜单列表
    * @Param: [userId]
    * @return: java.util.List<com.blockchain.server.system.dto.UserMenuDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/11 
    */
    List<UserMenuDto> userMenuListAll(String userId);

    /** 
    * @Description: 角色菜单 
    * @Param: [id] 
    * @return: java.lang.String 
    * @Author: Liu.sd 
    * @Date: 2019/3/15 
    */ 
    String[] menuListByRoleId(String id);
}
