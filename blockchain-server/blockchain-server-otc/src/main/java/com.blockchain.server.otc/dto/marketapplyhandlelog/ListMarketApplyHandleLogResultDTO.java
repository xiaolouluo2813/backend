package com.blockchain.server.otc.dto.marketapplyhandlelog;

import lombok.Data;

@Data
public class ListMarketApplyHandleLogResultDTO {
    private String id;
    private String marketApplyId;
    private String sysUserId;
    private String ipAddress;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
