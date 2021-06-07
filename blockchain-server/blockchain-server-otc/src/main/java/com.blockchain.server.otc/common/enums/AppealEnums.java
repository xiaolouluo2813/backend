package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 申诉状态
 */
@Getter
public enum AppealEnums {
    STATUS_NEW("NEW"), //新建
    STATUS_HANDLE("HANDLE"), //已处理
    TYPE_AD("AD"), //广告方申诉
    TYPE_ORDER("ORDER"), //订单方申诉
    RECEIPT_REMARK("卖家确认付款，自动取消申诉"), //确认付款时的申诉处理日志备注
    URL_APPEAL("/appeal"), //申诉图片上传地址
    ;

    private String value;

    AppealEnums(String value) {
        this.value = value;
    }
}
