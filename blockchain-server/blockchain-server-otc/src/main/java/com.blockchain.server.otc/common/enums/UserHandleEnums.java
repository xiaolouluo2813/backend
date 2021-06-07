package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 用户操作日志状态、类型
 */
@Getter
public enum UserHandleEnums {
    TYPE_PUBLISH("PUBLISH"), //发布广告
    TYPE_DEAL("DEAL"), //买入或出售
    TYPE_PAY("PAY"), //确认付款
    TYPE_RECEIPT("RECEIPT"), //确认收款
    TYPE_CANCEL("CANCEL"), //撤销
    TYPE_APPEAL("APPEAL"), //申诉
    TYPE_PENDING("PENDING"), //下架广告
    TYPE_DEFAULT("DEFAULT"), //上架广告
    LOG_TYPE_AD("AD"), //广告类型
    LOG_TYPE_ORDER("ORDER"), //订单类型
    ;

    private String value;

    UserHandleEnums(String value) {
        this.value = value;
    }
}
