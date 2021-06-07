package com.blockchain.server.cct.dto.tradingDetail;

import java.math.BigDecimal;

/***
 * 统计用户交易返回数据
 */
public class SelectStatResultDTO {
    private BigDecimal sumCharge; //手续费总和
    private BigDecimal sumAmount; //实际获得交易总额
    private String coinName;
    private String orderType;
    private String publishType;
    private String tradingType;
}
