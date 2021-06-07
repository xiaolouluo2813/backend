package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 用户支付信息类型
 */
@Getter
public enum UserPayEnums {
    TYPE_BANK("BANK"), //银行卡
    TYPE_WX("WX"), //微信
    TYPE_ZFB("ZFB"), //支付宝
    URL_WX("/userpay/wx"), //微信上传路径
    URL_ZFB("/userpay/zfb"), //微信上传路径
    ;

    private String value;

    UserPayEnums(String value) {
        this.value = value;
    }
}
