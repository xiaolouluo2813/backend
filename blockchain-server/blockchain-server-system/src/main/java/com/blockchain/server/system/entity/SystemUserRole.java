package com.blockchain.server.system.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户角色表
 * @author: Liusd
 * @create: 2019-03-04 18:08
 **/
@Data
@Table(name = "pc_auth_system_user_role")
public class SystemUserRole {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "role_id")
    private String roleId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
