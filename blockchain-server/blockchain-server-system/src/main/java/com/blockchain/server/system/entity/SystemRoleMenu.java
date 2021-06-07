package com.blockchain.server.system.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 角色菜单表
 * @author: Liusd
 * @create: 2019-03-04 18:06
 **/
@Data
@Table(name = "pc_auth_system_role_menu")
public class SystemRoleMenu extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "menu_id")
    private String menuTd;
    @Column(name = "create_name")
    private String createName;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
