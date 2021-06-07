package com.blockchain.server.databot.dto.matchconfig;

import lombok.Data;

@Data
public class ListMatchConfigResultDTO {
    private String id;
    private String userId;
    private String userName;
    private String coinName;
    private String unitName;
    private String minPrice;
    private String maxPrice;
    private String minPercent;
    private String maxPercent;
    private String priceType;
    private String status;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
