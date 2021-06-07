package com.blockchain.server.system.service;

import com.blockchain.server.system.entity.SystemUserRole;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:49
 **/
public interface SystemUserRoleService {

    /** 
    * @Description: 设置用户角色 
    * @Param: [roleIds, UserId] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    void setUserRoles(String roleIds, String UserId);

    /** 
    * @Description: 删除用户相关角色 
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/8 
    */ 
    void deleteByUserId(String id);

    /**
    * @Description: 根据角色id查询信息
    * @Param: [id]
    * @return: java.util.List<com.blockchain.server.system.entity.SystemUserRole>
    * @Author: Liu.sd
    * @Date: 2019/3/8
    */
    List<SystemUserRole> selectByRoleId(String id);

    /** 
    * @Description: 根据角色id删除中间表信息 
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/8 
    */ 
    void deleteByRoleId(String id);
}
