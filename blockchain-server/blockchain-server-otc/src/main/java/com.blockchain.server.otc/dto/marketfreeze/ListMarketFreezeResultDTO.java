package com.blockchain.server.otc.dto.marketfreeze;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListMarketFreezeResultDTO {
    private String id;
    private String marketApplyId;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String coinName;
    private BigDecimal amount;
    private java.util.Date createTime;
}
