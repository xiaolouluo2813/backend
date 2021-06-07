package com.blockchain.server.otc.common.enums;

import lombok.Getter;

@Getter
public enum MarketApplyEnums {

    TYPE_MARKET("MARKET"), //申请市商
    TYPE_CANCEL("CANCEL"), //申请取消市商

    STATUS_NEW("NEW"), //新建、待处理
    STATUS_AGREE("AGREE"), //同意
    STATUS_REJECT("REJECT"), //驳回
    ;

    private String value;

    MarketApplyEnums(String value) {
        this.value = value;
    }
}
