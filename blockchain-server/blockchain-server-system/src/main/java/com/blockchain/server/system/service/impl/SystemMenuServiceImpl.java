package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.common.enums.SystemResultEnums;
import com.blockchain.server.system.common.exception.SystemException;
import com.blockchain.server.system.dto.SystemMenuAddDto;
import com.blockchain.server.system.dto.SystemMenuResultDto;
import com.blockchain.server.system.dto.SystemRoleResultDto;
import com.blockchain.server.system.dto.UserMenuDto;
import com.blockchain.server.system.entity.SystemMenu;
import com.blockchain.server.system.mapper.SystemMenuMapper;
import com.blockchain.server.system.service.SystemMenuService;
import com.blockchain.server.system.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:49
 **/
@Service
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;


    @Autowired
    private SystemRoleService systemRoleService;

    @Override
    @Transactional
    public int insert(SystemMenuAddDto systemMenuAddDto) {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setId(UUID.randomUUID().toString());
        systemMenu.setName(systemMenuAddDto.getName());
        systemMenu.setCode(systemMenuAddDto.getCode());
        systemMenu.setUrl(systemMenuAddDto.getUrl());
        systemMenu.setPid(systemMenuAddDto.getPid());
        systemMenu.setType(systemMenuAddDto.getType());
        systemMenu.setIcon(systemMenuAddDto.getIcon());
        systemMenu.setRanking(systemMenuAddDto.getRanking());
        systemMenu.setStatus("Y");
        systemMenu.setCreateTime(new Date());
        systemMenu.setModifyTime(new Date());
        return systemMenuMapper.insert(systemMenu);
    }

    @Override
    public void codeCheck(String code) {
        SystemMenu systemMenu = new SystemMenu();
        systemMenu.setCode(code);
        systemMenu = systemMenuMapper.selectOne(systemMenu);
        if(systemMenu!=null){
            throw new SystemException(SystemResultEnums.CODE_EXISTS);
        }
    }

    @Override
    public List<SystemMenuResultDto> systemMenuList(String status) {
        List<SystemMenuResultDto> mList = systemMenuMapper.systeMenuListByStatusAndTypeAndPid(status,"M",null);
        for(SystemMenuResultDto msystemMenuResultDto : mList){
            List<SystemMenuResultDto> cList = systemMenuMapper.systeMenuListByStatusAndTypeAndPid(status,"C",msystemMenuResultDto.getId());
            for(SystemMenuResultDto csystemMenuResultDto : cList){
                List<SystemMenuResultDto> fList = systemMenuMapper.systeMenuListByStatusAndTypeAndPid(status,"F",csystemMenuResultDto.getId());
                csystemMenuResultDto.setChildren(fList);
            }
            msystemMenuResultDto.setChildren(cList);
        }
        return mList;
    }

    @Override
    public List<SystemMenuResultDto> roleMenuList(List<String> roleIds) {
        return systemMenuMapper.roleMenuList(roleIds);
    }

    @Override
    public List<SystemMenuResultDto> userMenuList(String status, String userId) {
        List<SystemRoleResultDto> roles = systemRoleService.userRoleList("Y",userId);
        List<String> roleIds = new ArrayList<>();
        List<SystemMenuResultDto> menus = new ArrayList<>();
        if (roles!=null && roles.size()>0){
            for(SystemRoleResultDto systemRole : roles){
                roleIds.add(systemRole.getId());
            }
            menus =roleMenuList(roleIds);
        }
        return menus;
    }

    @Override
    public List<UserMenuDto> userMenuListAll(String userId) {
        return systemMenuMapper.userMenuListAll(userId);
    }

    @Override
    public String[] menuListByRoleId(String id) {
        return systemMenuMapper.menuListByRoleId(id);
    }
}
