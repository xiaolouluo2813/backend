package com.blockchain.server.currency.common.enums;

import lombok.Getter;

public enum CurrencyEnums {
    CURRENCYPAIR_EXIST(0, "该币对已存在！"),
    CURRENCYPAIR_NULL(0, "该币对不存在！"),
    CURRENCY_EXIST(0, "该币种已存在！"),
    CURRENCY_NULL(0, "该币种不存在！"),
    ;

    @Getter
    private int code;
    @Getter
    private String msg;

    CurrencyEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
