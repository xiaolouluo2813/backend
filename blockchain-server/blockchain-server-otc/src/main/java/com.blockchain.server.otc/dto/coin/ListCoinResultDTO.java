package com.blockchain.server.otc.dto.coin;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListCoinResultDTO {
    private String id;
    private String coinName;
    private String unitName;
    private String coinNet;
    private Integer coinDecimal;
    private Integer unitDecimal;
    private BigDecimal coinServiceCharge;
    private String status;
    private Integer rank;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
