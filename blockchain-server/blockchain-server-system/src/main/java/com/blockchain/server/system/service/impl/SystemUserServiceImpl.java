package com.blockchain.server.system.service.impl;

import com.blockchain.common.base.util.MD5Utils;
import com.blockchain.server.system.common.constant.LoginConstant;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.common.util.RedisPrivateUtil;
import com.blockchain.server.system.dto.*;
import com.blockchain.server.system.entity.SystemUser;
import com.blockchain.server.system.mapper.SystemUserMapper;
import com.blockchain.server.system.service.SystemMenuService;
import com.blockchain.server.system.service.SystemRoleService;
import com.blockchain.server.system.service.SystemUserRoleService;
import com.blockchain.server.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Autowired
    private SystemRoleService systemRoleService;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    @Transactional
    public int insert(SystemUserAddDto systemUserAddDto) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(UUID.randomUUID().toString());
        systemUser.setAccount(systemUserAddDto.getAccount());
        systemUser.setUsername(systemUserAddDto.getUsername());
        systemUser.setPassword(MD5Utils.MD5(systemUserAddDto.getPassword()));
        systemUser.setSex(systemUserAddDto.getSex());
        systemUser.setPhone(systemUserAddDto.getPhone());
        systemUser.setEmail(systemUserAddDto.getEmail());
        systemUser.setStatus(systemUserAddDto.getStatus());
        systemUser.setModifyTime(new Date());
        systemUser.setCreateTime(new Date());
        return systemUserMapper.insert(systemUser);
    }

    @Override
    public void accountCheck(String account) {
        SystemUser systemUser = new SystemUser();
        systemUser.setAccount(account);
        systemUser = systemUserMapper.selectOne(systemUser);
        if (systemUser != null) {
            throw new SystemException(SystemResultEnums.ACCOUNT_EXISTS);
        }
    }

    @Override
    @Transactional
    public void update(SystemUserAddDto systemUserAddDto, String id) {
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(id);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_DOES_NOT_EXIST);
        }
        systemUser.setAccount(systemUserAddDto.getAccount() == null ? systemUser.getAccount() : systemUserAddDto.getAccount());
        systemUser.setUsername(systemUserAddDto.getUsername() == null ? systemUser.getUsername() : systemUserAddDto.getUsername());
        systemUser.setPassword(systemUserAddDto.getPassword() == null ? systemUser.getPassword() : systemUserAddDto.getPassword());
        systemUser.setSex(systemUserAddDto.getSex() == null ? systemUser.getSex() : systemUserAddDto.getSex());
        systemUser.setPhone(systemUserAddDto.getPhone() == null ? systemUser.getPhone() : systemUserAddDto.getPhone());
        systemUser.setEmail(systemUserAddDto.getEmail() == null ? systemUser.getEmail() : systemUserAddDto.getEmail());
        systemUser.setModifyTime(new Date());
        systemUserMapper.updateByPrimaryKey(systemUser);
    }

    @Override
    @Transactional
    public void updateStatus(String status, String id) {
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(id);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_DOES_NOT_EXIST);
        }
        systemUser.setStatus(status);
        systemUser.setModifyTime(new Date());
        systemUserMapper.updateByPrimaryKey(systemUser);
    }

    @Override
    public List<SystemUserResultDto> systemUserList(String status, String name, String phone) {
        return systemUserMapper.systemUserList(status,name,phone);
    }

    @Override
    public LoginDto login(String username, String password) {
        LoginDto loginDto = new LoginDto();
        SystemUser systemUser = selectByUsernameAndPAssword(username,password);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_NAME_OR_PASSWORD_ERROR);
        }else if ("N".equals(systemUser.getStatus())){
            throw new SystemException(SystemResultEnums.ACCOUNT_IS_DEACTIVATED);
        }
        /**
         * 用户角色1.0现行 1：1
         * 角色禁用时：用户不可登录，也不必查询角色菜单。
         */
        List<SystemRoleResultDto> roles = systemRoleService.userRoleList(null,systemUser.getId());
        List<UserMenuDto> userMenuDtos = new ArrayList<>();
        if (roles!=null && roles.size()>0){
            SystemRoleResultDto systemRoleResultDto = roles.get(0);
            if ("N".equals(systemRoleResultDto.getStatus())){
                throw new SystemException(SystemResultEnums.ROLE_IS_DEACTIVATED);
            }else {
                userMenuDtos =systemMenuService.userMenuListAll(systemUser.getId());
            }
        }
        loginDto.setMenus(userMenuDtos);
        loginDto.setUser(systemUser);
        return loginDto;
    }

    @Override
    public SystemUser selectByUsernameAndPAssword(String username, String password) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        systemUser = systemUserMapper.selectOne(systemUser);
        return systemUser;
    }

    @Override
    public SystemUser showById(String id) {
        return systemUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public SystemUser selectByUsernameAndPhone(String username, String phone) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPhone(phone);
        systemUser = systemUserMapper.selectOne(systemUser);
        return systemUser;
    }

    @Override
    public void setUserRoles(String roleIds, String id) {
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(id);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_DOES_NOT_EXIST);
        }
        //操作用户角色中间表数据
        systemUserRoleService.setUserRoles(roleIds,id);
    }

    @Override
    @Transactional
    public void updateUserPassword(String newPassword, String id) {
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(id);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_DOES_NOT_EXIST);
        }
        systemUser.setPassword(newPassword);
        systemUserMapper.updateByPrimaryKeySelective(systemUser);
    }

    @Override
    @Transactional
    public void deleteUser(String id) {
        SystemUser systemUser = systemUserMapper.selectByPrimaryKey(id);
        if (systemUser == null) {
            throw new SystemException(SystemResultEnums.USER_DOES_NOT_EXIST);
        }
        //删除用户信息
        systemUserMapper.delete(systemUser);
        //删除用户角色信息
        systemUserRoleService.deleteByUserId(id);
    }

    @Override
    public String getPublicKey(String username) {
        return RedisPrivateUtil.getPublicKey(LoginConstant.getLoginKey(username), redisTemplate);
    }
}
