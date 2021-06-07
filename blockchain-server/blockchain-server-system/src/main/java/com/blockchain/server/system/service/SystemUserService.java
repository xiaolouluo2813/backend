package com.blockchain.server.system.service;

import com.blockchain.server.system.dto.LoginDto;
import com.blockchain.server.system.dto.SystemUserAddDto;
import com.blockchain.server.system.dto.SystemUserResultDto;
import com.blockchain.server.system.entity.SystemUser;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
public interface SystemUserService {

    /**
    * @Description: 新增系统用户
    * @Param: [systemUserAddDto]
    * @return: int
    * @Author: Liu.sd
    * @Date: 2019/3/5
    */
    int insert(SystemUserAddDto systemUserAddDto);

    /**
    * @Description: 账户名唯一性校验
    * @Param: [account]
    * @return: void
    * @Author: Liu.sd
    * @Date: 2019/3/5
    */
    void accountCheck(String account);

    /**
    * @Description: 修改用户基本信息
    * @Param: [systemUserAddDto, id]
    * @return: void
    * @Author: Liu.sd
    * @Date: 2019/3/5
    */
    void update(SystemUserAddDto systemUserAddDto, String id);

    /**
    * @Description: 修改用户状态
    * @Param: [status, id]
    * @return: void
    * @Author: Liu.sd
    * @Date: 2019/3/5
    */
    void updateStatus(String status, String id);

    /**
    * @Description: 用户列表，可根据状态区分 默认全部
    * @Param: [status]
    * @return: java.util.List<com.blockchain.server.system.dto.SystemUserResultDto>
    * @Author: Liu.sd
    * @Date: 2019/3/5
    */
    List<SystemUserResultDto> systemUserList(String status, String name, String phone);

    /** 
    * @Description: 用户登录 
    * @Param: [username, password] 
    * @return: com.blockchain.server.system.dto.LoginDto 
    * @Author: Liu.sd 
    * @Date: 2019/3/6 
    */ 
    LoginDto login(String username, String password);

    /** 
    * @Description: 设置用户角色 
    * @Param: [roleIds, id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/5 
    */ 
    void setUserRoles(String roleIds, String id);

    /** 
    * @Description: 修改密码 
    * @Param: [newPassword, id]
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/7 
    */ 
    void updateUserPassword( String newPassword, String id);

    /** 
    * @Description: 删除用户 
    * @Param: [id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/8 
    */ 
    void deleteUser(String id);


    /**
     * 获取加密密码的公钥
     *
     * @param username
     * @return
     */
    String getPublicKey(String username);

    SystemUser selectByUsernameAndPAssword(String username, String password);

    SystemUser showById(String id);

    SystemUser selectByUsernameAndPhone(String username, String phone);
}
