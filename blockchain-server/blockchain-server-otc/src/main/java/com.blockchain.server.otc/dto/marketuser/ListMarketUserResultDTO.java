package com.blockchain.server.otc.dto.marketuser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListMarketUserResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String realName;
    private String nickName;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
