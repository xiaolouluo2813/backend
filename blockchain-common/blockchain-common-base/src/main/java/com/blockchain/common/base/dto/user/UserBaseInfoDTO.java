package com.blockchain.common.base.dto.user;

import lombok.Data;

/**
 * 用户信息DTO
 */
@Data
public class UserBaseInfoDTO {
    private String userId; // 用户标识
    private String international; // 国家
//    private String internationalCode; // 手机国际区号
    private String email; // 绑定的邮箱
    private String realName;   // 真实姓名
    private String mobilePhone;
//    private String lowAuth;
//    private String highAuth;
    //TODO 初级认证状态、高级认证状态
}
