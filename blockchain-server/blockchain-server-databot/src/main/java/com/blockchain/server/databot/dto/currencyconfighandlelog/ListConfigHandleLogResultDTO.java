package com.blockchain.server.databot.dto.currencyconfighandlelog;

import lombok.Data;

@Data
public class ListConfigHandleLogResultDTO {
    private String id;
    private String currencyPair;
    private String handleType;
    private String sysUserId;
    private String ipAddress;
    private Float beforeKChangePercent;
    private Float afterKChangePercent;
    private Float beforeKMaxChangePercent;
    private Float afterKMaxChangePercent;
    private Float beforeKDayTotalAmount;
    private Float afterKDayTotalAmount;
    private Float beforeBuyPricePercent;
    private Float afterBuyPricePercent;
    private Float beforeBuyTotalAmount;
    private Float afterBuyTotalAmount;
    private Float beforeSellPricePercent;
    private Float afterSellPricePercent;
    private Float beforeSellTotalAmount;
    private Float afterSellTotalAmount;
    private Float beforeKMaxPrice;
    private Float afterKMaxPrice;
    private Float beforeKMinPrice;
    private Float afterKMinPrice;
    private Float beforeBuyMaxPrice;
    private Float afterBuyMaxPrice;
    private Float beforeBuyMinPrice;
    private Float afterBuyMinPrice;
    private Float beforeSellMaxPrice;
    private Float afterSellMaxPrice;
    private Float beforeSellMinPrice;
    private Float afterSellMinPrice;
    private String beforePriceType;
    private String afterPriceType;
    private java.util.Date createTime;
}
