package com.blockchain.server.cct.dto.coin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinParamDTO {
    private String sysUserId;
    private String ipAddr;
    private String coinName;
    private String unitName;
    private String coinNet;
    private String unitNet;
    private BigDecimal coinDecimals;
    private BigDecimal unitDecimals;
    private String status;
    private Integer rank;
    private String tag;
}
