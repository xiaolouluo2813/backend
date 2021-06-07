package com.blockchain.server.otc.dto.orderhandlelog;

import lombok.Data;

@Data
public class ListOrderHandleLogResultDTO {
    private String id;
    private String orderNumber;
    private String sysUserId;
    private String ipAddress;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
