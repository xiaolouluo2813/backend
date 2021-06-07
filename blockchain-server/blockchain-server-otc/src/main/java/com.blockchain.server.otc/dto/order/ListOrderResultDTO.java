package com.blockchain.server.otc.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListOrderResultDTO {
    private String id;
    private String orderNumber;
    private String adId;
    private String coinName;
    private String unitName;
    private String buyUserId;
    private String buyUserName;
    private String buyRealName;
    private String buyNickName;
    private String buyStatus;
    private String sellUserId;
    private String sellUserName;
    private String sellRealName;
    private String sellNickName;
    private String sellStatus;
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal turnover;
    private BigDecimal chargeRatio;
    private String orderType;
    private String orderStatus;
    private String orderPayType;
    private java.util.Date createTime;
    private java.util.Date modifyTime;
}
