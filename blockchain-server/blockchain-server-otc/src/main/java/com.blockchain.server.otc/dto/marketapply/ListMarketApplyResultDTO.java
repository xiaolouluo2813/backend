package com.blockchain.server.otc.dto.marketapply;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListMarketApplyResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String coinName;
    private BigDecimal amount;
    private String applyType;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
