package com.blockchain.server.databot.dto.currencyconfig;

import lombok.Data;

@Data
public class InsertConfigParamDTO {
    private String currencyPair;
    private String status;
    private String priceType;
    private Float kchangePercent;
    private Float kmaxChangePercent;
    private Float kdayTotalAmount;
    private Float kmaxPrice;
    private Float kminPrice;
    private Float buyPricePercent;
    private Float buyTotalAmount;
    private Float buyMaxPrice;
    private Float buyMinPrice;
    private Float sellPricePercent;
    private Float sellTotalAmount;
    private Float sellMaxPrice;
    private Float sellMinPrice;
}
