package com.blockchain.server.otc.dto.coinhandlelog;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListCoinHandleLogResultDTO {
    private String id;
    private String coinId;
    private String sysUserId;
    private String ipAddress;
    private String beforeCoinName;
    private String afterCoinName;
    private String beforeUnitName;
    private String afterUnitName;
    private String beforeCoinNet;
    private String afterCoinNet;
    private Integer beforeCoinDecimal;
    private Integer afterCoinDecimal;
    private Integer beforeUnitDecimal;
    private Integer afterUnitDecimal;
    private BigDecimal beforeCoinServiceCharge;
    private BigDecimal afterCoinServiceCharge;
    private String beforeStatus;
    private String afterStatus;
    private java.util.Date createTime;
}
