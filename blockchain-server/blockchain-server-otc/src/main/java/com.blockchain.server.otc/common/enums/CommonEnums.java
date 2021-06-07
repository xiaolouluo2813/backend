package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 公共
 */
@Getter
public enum CommonEnums {
    STATUS_YES("Y"), //可用状态
    STATUS_NO("N"), //禁用状态
    DECIMAL_DISH("0.1"), //计算的偏差值
    ;

    private String value;

    CommonEnums(String value) {
        this.value = value;
    }
}
