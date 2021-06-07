package com.blockchain.server.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/7 10:52
 * @user WIN10
 */
@Data
public class AuthenticationDto {
    private String id;
    private String nickName;
    private String international;
    private String mobilePhone;
    private String idType;
    private String realName;
    private String fileUrl;
    private String fileUrl2;
    private String status;
    private String remark;
    private String auth;
    private Date createTime;
}
