package com.blockchain.server.otc.common.enums;

import lombok.Getter;

@Getter
public enum MarketUserEnums {

    STATUS_NOTMARKET("NOTMARKET"), //未认证
    STATUS_MARKET("MARKET"), //认证
    STATUS_CANCELING("CANCELING"), //取消中
    ;

    private String value;

    MarketUserEnums(String value) {
        this.value = value;
    }
}
