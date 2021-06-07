package com.blockchain.server.otc.dto.bill;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListBillResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String recordNumber;
    private BigDecimal freezeBalance;
    private BigDecimal freeBalance;
    private String billType;
    private String coinName;
    private java.util.Date createTime;
}
