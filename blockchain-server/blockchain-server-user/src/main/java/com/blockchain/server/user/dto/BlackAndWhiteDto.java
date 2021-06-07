package com.blockchain.server.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/5 12:08
 * @user WIN10
 */
@Data
public class BlackAndWhiteDto {
    private String id;
    // 名单类型:BLACK(黑名单)、WHITE(白名单)
    private String listType;
    // 昵称
    private String nickName;
    // 手机号码
    private String mobilePhone;
    // 手机国际区号
    private String international;
    // 创建时间
    private Date createTime;
    // 证件类型
    private String idType;
    // 证件号
    private String idNumber;
    // 真实姓名
    private String realName;
    // 类型：登录、提现等
    private String type;
}
