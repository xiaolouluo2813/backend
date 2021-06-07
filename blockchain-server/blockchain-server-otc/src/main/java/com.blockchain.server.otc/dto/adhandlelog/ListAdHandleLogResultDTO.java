package com.blockchain.server.otc.dto.adhandlelog;

import lombok.Data;

@Data
public class ListAdHandleLogResultDTO {
    private String id;
    private String adNumber;
    private String sysUserId;
    private String ipAddress;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
