package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.entity.SystemUserRole;
import com.blockchain.server.system.mapper.SystemUserRoleMapper;
import com.blockchain.server.system.service.SystemUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:52
 **/
@Service
public class SystemUserRoleServiceImpl implements SystemUserRoleService {

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Override
    @Transactional
    public void setUserRoles(String roleIds, String UserId) {
        SystemUserRole dsystemUserRole = new SystemUserRole();
        dsystemUserRole.setUserId(UserId);
        systemUserRoleMapper.delete(dsystemUserRole);
        String[] ids = roleIds.split(",");
        for (String roleId : ids) {
            SystemUserRole systemUserRole = new SystemUserRole();
            systemUserRole.setId(UUID.randomUUID().toString());
            systemUserRole.setUserId(UserId);
            systemUserRole.setRoleId(roleId);
            systemUserRole.setCreateTime(new Date());
            systemUserRole.setModifyTime(new Date());
            systemUserRoleMapper.insert(systemUserRole);
        }
    }

    @Override
    @Transactional
    public void deleteByUserId(String id) {
        SystemUserRole dsystemUserRole = new SystemUserRole();
        dsystemUserRole.setUserId(id);
        systemUserRoleMapper.delete(dsystemUserRole);
    }

    @Override
    public List<SystemUserRole> selectByRoleId(String id) {
        SystemUserRole systemUserRole = new SystemUserRole();
        systemUserRole.setRoleId(id);
        return systemUserRoleMapper.select(systemUserRole);
    }

    @Override
    @Transactional
    public void deleteByRoleId(String id) {
        SystemUserRole dsystemUserRole = new SystemUserRole();
        dsystemUserRole.setRoleId(id);
        systemUserRoleMapper.delete(dsystemUserRole);
    }
}
