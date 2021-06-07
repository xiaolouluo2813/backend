package com.blockchain.server.otc.dto.appealhandlelog;

import lombok.Data;

@Data
public class ListAppealHandleLogResultDTO {
    private String id;
    private String orderNumber;
    private String sysUserId;
    private String ipAddress;
    private String afterStatus;
    private String remark;
    private java.util.Date createTime;
}
