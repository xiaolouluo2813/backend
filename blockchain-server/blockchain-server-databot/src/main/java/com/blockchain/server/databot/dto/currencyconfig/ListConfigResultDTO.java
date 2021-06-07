package com.blockchain.server.databot.dto.currencyconfig;

import lombok.Data;

import java.util.Date;

@Data
public class ListConfigResultDTO {
    private String id;
    private String currencyPair;
    private String status;
    private String priceType;
    private Float kchangePercent;
    private Float kmaxChangePercent;
    private Float kdayTotalAmount;
    private Float kmaxPrice;
    private Float kminPrice;
    private Float buyMaxPrice;
    private Float buyMinPrice;
    private Float sellMaxPrice;
    private Float sellMinPrice;
    private Float buyPricePercent;
    private Float buyTotalAmount;
    private Float sellPricePercent;
    private Float sellTotalAmount;
    private java.util.Date createTime;
    private java.util.Date modifyTime;

}
