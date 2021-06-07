package com.blockchain.server.otc.dto.coin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateCoinParamDTO {
    private String id;
    private String sysUserId;
    private String ipAddress;
    private String coinName;
    private String unitName;
    private String coinNet;
    private Integer coinDecimal;
    private Integer unitDecimal;
    private BigDecimal coinServiceCharge;
    private String status;
    private Integer rank;
}
