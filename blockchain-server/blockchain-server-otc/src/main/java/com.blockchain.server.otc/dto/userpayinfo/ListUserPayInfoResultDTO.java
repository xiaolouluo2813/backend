package com.blockchain.server.otc.dto.userpayinfo;

import lombok.Data;

@Data
public class ListUserPayInfoResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String payType;
    private String accountInfo;
    private String collectionCodeUrl;
    private String bankNumber;
    private String bankUserName;
    private String bankType;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
