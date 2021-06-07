package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 订单相关状态、类型
 */
@Getter
public enum OrderEnums {
    STATUS_NEW("NEW"), //订单状态和用户操作状态：新建
    STATUS_UNDERWAY("UNDERWAY"), //订单状态：进行中
    STATUS_FINISH("FINISH"), //订单状态：已完成
    STATUS_CANCEL("CANCEL"), //订单状态和用户操作状态：已撤销
    STATUS_APPEAL("APPEAL"), //订单状态和用户操作状态：申诉
    STATUS_CONFIRM("CONFIRM"), //用户操作状态：已确认
    TYPE_BUY("BUY"), //买单
    TYPE_SELL("SELL"), //卖单
    ;

    private String value;

    OrderEnums(String value) {
        this.value = value;
    }
}
