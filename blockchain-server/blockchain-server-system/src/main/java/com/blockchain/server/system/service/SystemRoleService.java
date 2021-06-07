package com.blockchain.server.system.service;

import com.blockchain.server.system.dto.SystemRoleResultDto;
import com.blockchain.server.system.dto.UserMenuDto;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:49
 **/
public interface SystemRoleService {
    
    /** 
    * @Description: 系统角色列表
    * @Param: [status, name] 
    * @return: java.util.List<com.blockchain.server.system.dto.SystemRoleResultDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/7 
    */ 
    List<SystemRoleResultDto> systemRoleList(String status, String name);

    /** 
    * @Description: 新增角色 
    * @Param: [code, name, ranking] 
    * @return: int 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    int insert(String code, String name, Integer ranking);

    /** 
    * @Description: 角色标识唯一验证 
    * @Param: [code] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void codeCheck(String code);

    /** 
    * @Description: 修改角色信息 
    * @Param: [code, name, ranking, id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void update(String code, String name, Integer ranking, String id);

    /** 
    * @Description: 修改角色状态 
    * @Param: [status, id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void updateStatus(String status, String id);

    /** 
    * @Description: 用户角色列表 
    * @Param: [status, id]
    * @return: java.util.List<com.blockchain.server.system.dto.SystemRoleResultDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    List<SystemRoleResultDto> userRoleList(String status,String id);

    /** 
    * @Description: 设置角色菜单 
    * @Param: [menuIds, id, account] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void setRoleMenus(String menuIds, String id, String account);

    /** 
    * @Description: 删除角色 
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/8 
    */ 
    void deleteRole(String id);

    /**
    * @Description:  角色权限数组
    * @Param: [id]
    * @return: java.lang.String[]
    * @Author: Liu.sd
    * @Date: 2019/3/18
    */
    String[] roleMenuList(String id);
}
