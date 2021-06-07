package com.blockchain.server.system.service;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:49
 **/
public interface SystemRoleMenuService {
    /** 
    * @Description: 设置角色菜单 
    * @Param: [menuIds, roleId, account] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void setRoleMenus(String menuIds, String roleId, String account);

    /** 
    * @Description: 删除角色菜单信息 
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/8 
    */ 
    void deleteByRoleId(String id);
}
