package com.blockchain.server.databot.dto.matchconfig;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InsertMatchConfigParamDTO {
    private String userName;
    private String coinName;
    private String unitName;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal minPercent;
    private BigDecimal maxPercent;
    private String priceType;
    private String status;
}
