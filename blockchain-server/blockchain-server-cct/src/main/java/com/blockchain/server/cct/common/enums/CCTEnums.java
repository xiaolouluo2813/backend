package com.blockchain.server.cct.common.enums;

import lombok.Getter;

public enum CCTEnums {
    REPEAL_STATUS_ERROR(0, "撤单失败，当前订单状态无法撤销！"),
    HANDLE_WALLET_ERROR(0, "操作失败，钱包处理出现未知主网标识！"),
    COIN_NULL(0, "操作失败，交易对不存在！"),
    AUTOMATICDATA_NOT_NULL(0, "操作失败，已存在相同盘口规则！"),
    COMMISSION_ISSUE_TIME_TYPE_ERROR(0, "请选择正确的发放类型！"),
    COMMISSION_ISSUE_TIME_HOUR_NULL(0, "请输入每天发放的小时数！"),
    COMMISSION_ISSUE_TIME_HOUR_ERROR(0, "请输入0-23内的小时数！"),
    COMMISSION_ISSUE_TIME_DAY_NULL(0, "请输入发放的日期数！"),
    COMMISSION_ISSUE_TIME_DAY_ERROR(0, "请输入1-28内的日期数！"),
	NET_ERROR(0,"网络繁忙，请稍后重试"),
    CANCEL_ORDER_FAIL(0,"撤单失败"),
    QUANTIZED_CLOSING(0,"量化关闭中，请稍后重试"),
    ORDER_CANCEL_ERROR(0,"撤单失败！"),
    ;

    @Getter
    private int code;
    @Getter
    private String msg;

    CCTEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
