package com.blockchain.server.system.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户返回Dto
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Data
public class SystemUserResultDto implements Serializable {

    /**
     * id
     */
    private String id;
    /**
     * 账户名
     */
    private String account;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 0女1男2保密
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态  可用(Y)、禁用(N)
     */
    private String status;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 创建时间
     */
    private Date createTime;
}
