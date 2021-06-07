package com.blockchain.server.cct.dto.commission;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListCommissionResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String nickName;
    private String realName;
    private String pid;
    private String puserName;
    private String pnickName;
    private String prealName;
    private String recordId;
    private BigDecimal amount;
    private String coinName;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
