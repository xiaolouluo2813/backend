package com.blockchain.server.otc.dto.confighandlelog;

import lombok.Data;

@Data
public class ListConfigHandleLogResultDTO {
    private String id;
    private String sysUserId;
    private String ipAddress;
    private String dataKey;
    private String beforeValue;
    private String afterValue;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
