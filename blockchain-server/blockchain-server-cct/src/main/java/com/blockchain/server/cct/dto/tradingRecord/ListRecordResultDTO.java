package com.blockchain.server.cct.dto.tradingRecord;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/***
 * 成交记录列表返回参数
 */
@Data
public class ListRecordResultDTO {
    private String id; //成交记录id
    private String makerId; //挂单id
    private String makerDetailId; //挂单成交详情id
    private BigDecimal makerPrice; //挂单单价
    private BigDecimal makerRealAmount; //挂单实际获得
    private BigDecimal makerCharge; //挂单手续费
    private BigDecimal makerChargeRatio; //挂单手续费比例
    private String makerCoin; //挂单获得货币
    private String takerId; //吃单id
    private String takerDetailId; //吃单成交详情id
    private BigDecimal takerPrice; //吃单单价
    private BigDecimal takerRealAmount; //吃单实际获得
    private BigDecimal takerCharge; //吃单手续费
    private BigDecimal takerChargeRatio; //吃单手续费比例
    private String takerCoin; //吃单获得货币
    private BigDecimal tradingNum; //撮合数量
    private Date createTime; //创建时间
}
