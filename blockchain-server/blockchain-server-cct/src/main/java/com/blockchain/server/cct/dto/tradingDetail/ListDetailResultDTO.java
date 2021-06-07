package com.blockchain.server.cct.dto.tradingDetail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 成交记录详细列表返回
 */
@Data
public class ListDetailResultDTO {
    private String recordId; //成交记录id
    private String makerDetailId; //挂单成交详情id
    private BigDecimal makerPrice; //挂单单价
    private BigDecimal makerRealAmount; //挂单实际获得
    private BigDecimal makerCharge; //挂单手续费
    private BigDecimal makerChargeRatio; //挂单手续费比例
    private String takerDetailId; //吃单成交详情id
    private BigDecimal takerPrice; //吃单单价
    private BigDecimal takerRealAmount; //吃单实际获得
    private BigDecimal takerCharge; //吃单手续费
    private BigDecimal takerChargeRatio; //吃单手续费比例
    private BigDecimal tradingNum; //撮合数量
    private String coinName; //基本货币
    private String unitName; //二级货币
    private Date createTime; //创建时间
}
