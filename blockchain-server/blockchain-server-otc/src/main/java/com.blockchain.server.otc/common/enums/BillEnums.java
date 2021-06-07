package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 资金对账表类型
 */
@Getter
public enum BillEnums {
    TYPE_PUBLISH("PUBLISH"), //发布广告
    TYPE_DEAL("DEAL"), //买入或卖出
    TYPE_MARK("MARK"), //成交
    TYPE_CANCEL("CANCEL"), //撤销
    TYPE_AUTO_CANCEL("AUTO_CANCEL"),//自动撤销
    MARKET_USER("MARKET_USER"),//成为市商
    CANCEL_MARKET_USER("CANCEL_MARKET_USER"),//取消市商
    ;
    private String value;

    BillEnums(String value) {
        this.value = value;
    }
}
