package com.blockchain.server.otc.common.enums;

import lombok.Getter;

public enum OtcEnums {
    USER_NULL(9801, "用户不存在！"),
    CANCEL_AD_ORDERS_UNFINISHED(9802, "操作失败，当前广告还有未完成订单！"),
    COIN_NULL(9803, "当前平台不支持该货币交易！"),
    CONFIG_NULL(9804, "配置信息不存在！"),
    WALLET_COIN_NET_ERROR(9805, "操作失败，钱包处理出现未知主网标识！"),
    APPEAL_STATUS_NOT_NEW(9806, "操作失败，申诉记录已处理！"),
    APPEAL_ORDER_STATUS_NOT_APPEAL(9807, "操作失败，订单状态已更新！"),
    AD_CANCEL_STATUS_NOT_DEFAULT(9808, "操作失败，广告状态已更新！"),
    MARKET_USER_EXIST(9809, "操作失败，用户已存在市商列表中！"),
    MARKET_FREEZE_LESS_THAN_ZERO(9810, "请输入大于零的数量！"),
    MARKET_FREEZE_COIN_CONFIG_NULL(9811, "操作失败，保证金代币配置不存在，请手动输入保证金代币！"),
    MARKET_USER_NULL(9812, "操作失败，市商用户不存在！"),
    MARKET_FREEZE_NULL(9813, "操作失败，保证金记录不存在！"),
    MARKET_APPLY_NULL(9814, "操作失败，申请记录不存在请刷新重试！"),
    ;

    @Getter
    private int code;
    @Getter
    private String msg;

    OtcEnums(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
