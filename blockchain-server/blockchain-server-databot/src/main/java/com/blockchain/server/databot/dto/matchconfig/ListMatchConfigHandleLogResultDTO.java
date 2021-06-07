package com.blockchain.server.databot.dto.matchconfig;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListMatchConfigHandleLogResultDTO {
    private String id;
    private String sysUserId;
    private String ipAddress;
    private String beforeUserId;
    private String afterUserId;
    private String beforeUserName;
    private String afterUserName;
    private String beforeCoinName;
    private String afterCoinName;
    private String beforeUnitName;
    private String afterUnitName;
    private BigDecimal beforeMinPrice;
    private BigDecimal afterMinPrice;
    private BigDecimal beforeMaxPrice;
    private BigDecimal afterMaxPrice;
    private BigDecimal beforeMinPercent;
    private BigDecimal afterMinPercent;
    private BigDecimal beforeMaxPercent;
    private BigDecimal afterMaxPercent;
    private String beforePriceType;
    private String afterPriceType;
    private String beforeStatus;
    private String afterStatus;
    private String handleType;
    private java.util.Date createTime;
}
