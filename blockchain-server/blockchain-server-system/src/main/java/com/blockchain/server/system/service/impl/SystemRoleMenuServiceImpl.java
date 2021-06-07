package com.blockchain.server.system.service.impl;

import com.blockchain.server.system.entity.SystemRoleMenu;
import com.blockchain.server.system.mapper.SystemRoleMenuMapper;
import com.blockchain.server.system.service.SystemRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-04 19:52
 **/
@Service
public class SystemRoleMenuServiceImpl implements SystemRoleMenuService {

    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    @Transactional
    public void setRoleMenus(String menuIds, String roleId, String account) {
        SystemRoleMenu dsystemRoleMenu = new SystemRoleMenu();
        dsystemRoleMenu.setRoleId(roleId);
        //根据角色id删除菜单
        systemRoleMenuMapper.delete(dsystemRoleMenu);
        String[] ids = menuIds.split(",");
        for (String menuId : ids) {
            //新增角色菜单数据
            SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
            systemRoleMenu.setId(UUID.randomUUID().toString());
            systemRoleMenu.setMenuTd(menuId);
            systemRoleMenu.setRoleId(roleId);
            systemRoleMenu.setCreateName(account);
            systemRoleMenu.setCreateTime(new Date());
            systemRoleMenu.setModifyTime(new Date());
            systemRoleMenuMapper.insert(systemRoleMenu);
        }
    }

    @Override
    @Transactional
    public void deleteByRoleId(String id) {
        SystemRoleMenu dsystemRoleMenu = new SystemRoleMenu();
        dsystemRoleMenu.setRoleId(id);
        systemRoleMenuMapper.delete(dsystemRoleMenu);
    }
}
