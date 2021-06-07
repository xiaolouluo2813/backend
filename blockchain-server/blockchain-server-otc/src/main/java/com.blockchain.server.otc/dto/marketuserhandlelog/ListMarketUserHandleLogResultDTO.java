package com.blockchain.server.otc.dto.marketuserhandlelog;

import lombok.Data;

@Data
public class ListMarketUserHandleLogResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String sysUserId;
    private String ipAddress;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
