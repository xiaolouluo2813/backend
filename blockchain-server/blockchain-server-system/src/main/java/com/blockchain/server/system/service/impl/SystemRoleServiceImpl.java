package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.dto.SystemRoleResultDto;
import com.blockchain.server.system.dto.UserMenuDto;
import com.blockchain.server.system.entity.SystemRole;
import com.blockchain.server.system.entity.SystemUserRole;
import com.blockchain.server.system.mapper.SystemRoleMapper;
import com.blockchain.server.system.service.SystemMenuService;
import com.blockchain.server.system.service.SystemRoleMenuService;
import com.blockchain.server.system.service.SystemRoleService;
import com.blockchain.server.system.service.SystemUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:51
 **/
@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemRoleMenuService systemRoleMenuService;

    @Autowired
    private SystemUserRoleService systemUserRoleService;

    @Autowired
    private SystemMenuService systemMenuService;

    @Override
    public List<SystemRoleResultDto> systemRoleList(String status, String name) {
        return systemRoleMapper.systemRoleList(status,name);
    }

    @Override
    @Transactional
    public int insert(String code, String name, Integer ranking) {
        SystemRole systemRole = new SystemRole();
        systemRole.setId(UUID.randomUUID().toString());
        systemRole.setCode(code);
        systemRole.setName(name);
        systemRole.setRanking(ranking);
        systemRole.setStatus("Y");
        systemRole.setModifyTime(new Date());
        systemRole.setCreateTime(new Date());
        return systemRoleMapper.insert(systemRole);
    }

    @Override
    public void codeCheck(String code) {
        SystemRole systemRole = new SystemRole();
        systemRole.setCode(code);
        systemRole = systemRoleMapper.selectOne(systemRole);
        if(systemRole!=null){
            throw new SystemException(SystemResultEnums.CODE_EXISTS);
        }
    }

    @Override
    @Transactional
    public void update(String code, String name, Integer ranking, String id) {
        SystemRole systemRole = systemRoleMapper.selectByPrimaryKey(id);
        if (systemRole==null){
            throw new SystemException(SystemResultEnums.ROLE_DOES_NOT_EXIST);
        }
        systemRole.setCode(code==null?systemRole.getCode():code);
        systemRole.setName(name==null?systemRole.getName():name);
        systemRole.setRanking(ranking==null?systemRole.getRanking():ranking);
        systemRole.setModifyTime(new Date());
        systemRoleMapper.updateByPrimaryKey(systemRole);
    }

    @Override
    @Transactional
    public void updateStatus(String status, String id) {
        SystemRole systemRole = systemRoleMapper.selectByPrimaryKey(id);
        if (systemRole==null){
            throw new SystemException(SystemResultEnums.ROLE_DOES_NOT_EXIST);
        }
        systemRole.setStatus(status);
        systemRole.setModifyTime(new Date());
        systemRoleMapper.updateByPrimaryKey(systemRole);
    }

    @Override
    public List<SystemRoleResultDto> userRoleList(String status,String id) {
        return  systemRoleMapper.userRoleList(status,id);
    }

    @Override
    public void setRoleMenus(String menuIds, String id, String account) {
        SystemRole systemRole = systemRoleMapper.selectByPrimaryKey(id);
        if (systemRole==null){
            throw new SystemException(SystemResultEnums.ROLE_DOES_NOT_EXIST);
        }
        //???????????????????????????
        systemRoleMenuService.setRoleMenus(menuIds,id,account);
    }

    @Override
    @Transactional
    public void deleteRole(String id) {
        SystemRole systemRole = systemRoleMapper.selectByPrimaryKey(id);
        if (systemRole==null){
            throw new SystemException(SystemResultEnums.ROLE_DOES_NOT_EXIST);
        }
        //????????????????????????
//        List<SystemUserRole> list = systemUserRoleService.selectByRoleId(id);
//        if (list.size()>0){
//            throw new SystemException(SystemResultEnums.ROLE_OCCUPIED);
//        }
        //????????????????????????
        systemUserRoleService.deleteByRoleId(id);
        //??????????????????
        systemRoleMapper.delete(systemRole);
        //????????????????????????
        systemRoleMenuService.deleteByRoleId(id);
    }

    @Override
    public String[] roleMenuList(String id) {
        SystemRole systemRole = systemRoleMapper.selectByPrimaryKey(id);
        if (systemRole==null){
            throw new SystemException(SystemResultEnums.ROLE_DOES_NOT_EXIST);
        }
        return systemMenuService.menuListByRoleId(id);
    }
}
