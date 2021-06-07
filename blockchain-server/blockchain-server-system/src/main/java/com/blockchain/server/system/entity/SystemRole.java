package com.blockchain.server.system.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 角色表
 * @author: Liusd
 * @create: 2019-03-04 17:43
 **/
@Data
@Table(name = "pc_auth_system_role")
public class SystemRole extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "ranking")
    private Integer ranking;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
