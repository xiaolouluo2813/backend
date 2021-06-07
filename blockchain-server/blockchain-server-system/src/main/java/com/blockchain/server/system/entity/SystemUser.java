package com.blockchain.server.system.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统用户表
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Data
@Table(name = "pc_auth_system_user")
public class SystemUser extends BaseModel {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "account")
    private String account;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
