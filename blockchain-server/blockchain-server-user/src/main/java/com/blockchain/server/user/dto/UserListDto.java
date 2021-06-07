package com.blockchain.server.user.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Harvey
 * @date 2019/3/4 19:01
 * @user WIN10
 */
@Data
public class UserListDto {
    private String userId;
    private String email;
    private Character lowAuth;
    private Character highAuth;
    private Date createTime;
    private String idType;
    private String idNumber;
    private String mobilePhone;
    private String realName;
    private String international;
    private String internationalCode;
    private String invitationCode;//邀请码
    private Integer incrCode;
    private Integer randomNumber;
    private String nickName;
    // 黑白名单
    private String loginListType;
    // 登录状态
    private String loginType;
    // 黑白名单
    private String txListType;
    // 交易状态
    private String txType;
    // 初级审核时间
    private Date lowAuthTime;
    // 高级审核时间
    private Date highAuthTime;
    private String pMobilePhone;
    private String pNickName;
}
