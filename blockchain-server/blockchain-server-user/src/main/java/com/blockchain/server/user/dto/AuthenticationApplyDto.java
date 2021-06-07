package com.blockchain.server.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/7 10:52
 * @user WIN10
 */
@Data
public class AuthenticationApplyDto {
    private String userId;
    private String idType;
    private String idNumber;
    // 初级认证存放地址
    private String fileUrl1;
    private String fileUrl2;
    private String lowStatus;
    private Date lowCreateTime;
    // 高级认证存放地址
    private String fileUrl;
    private String highStatus;
    private Date highCreateTime;
}
