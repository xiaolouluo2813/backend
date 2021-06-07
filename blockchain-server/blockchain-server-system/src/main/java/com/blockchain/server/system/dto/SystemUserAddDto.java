package com.blockchain.server.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户新增Dto
 * @author: Liusd
 * @create: 2019-03-04 17:34
 **/
@Data
public class SystemUserAddDto implements Serializable {

    /**
     * 账户名
     */
    private String account;

    private String id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
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
     * 状态 可用(Y)、禁用(N)
     */
    private String status;
}
