package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 广告表相关状态、类型
 */
@Getter
public enum AdEnums {
    STATUS_DEFAULT("DEFAULT"),//挂单中
    STATUS_PENDING("PENDING"),//交易中
    STATUS_UNDERWAY("UNDERWAY"), //进行中
    STATUS_FINISH("FINISH"), //已完成
    STATUS_CANCEL("CANCEL"), //已撤销
    TYPE_BUY("BUY"), //买单
    TYPE_SELL("SELL"), //卖单
    ;

    private String value;

    AdEnums(String value) {
        this.value = value;
    }

}
