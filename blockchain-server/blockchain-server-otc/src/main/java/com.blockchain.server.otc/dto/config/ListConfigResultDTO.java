package com.blockchain.server.otc.dto.config;

import lombok.Data;

@Data
public class ListConfigResultDTO {
    private String id;
    private String dataKey;
    private String dataValue;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
