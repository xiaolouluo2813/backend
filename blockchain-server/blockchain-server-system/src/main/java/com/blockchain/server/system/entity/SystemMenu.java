package com.blockchain.server.system.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 菜单表
 * @author: Liusd
 * @create: 2019-03-04 17:51
 **/
@Data
@Table(name = "pc_auth_system_menu")
public class SystemMenu extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "url")
    private String url;
    @Column(name = "pid")
    private String pid;
    @Column(name = "type")
    private String type;
    @Column(name = "icon")
    private String icon;
    @Column(name = "ranking")
    private Integer ranking;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;
}
