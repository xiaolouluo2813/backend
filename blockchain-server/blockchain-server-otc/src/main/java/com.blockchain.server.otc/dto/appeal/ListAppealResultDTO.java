package com.blockchain.server.otc.dto.appeal;

import lombok.Data;

@Data
public class ListAppealResultDTO {
    private String id;
    private String orderNumber;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
