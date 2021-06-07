package com.blockchain.server.otc.common.enums;

import lombok.Getter;

/***
 * 配置信息的Key
 */
@Getter
public enum ConfigEnums {
    AD_SERVICE_CHARGE("ad_service_charge"), //广告方是否收取手续费
    ORDER_SERVICE_CHARGE("order_service_charge"), //订单方是否收取手续费
    AUTO_CANCEL("auto_cancel"), //自动撤单是否开启
    AUTO_CANCEL_INTERVAL("auto_cancel_interval"), //自动撤单时间间隔
    MARKET_SELL_COUNT("market_sell_ad_count"), //市商可发布多少卖单
    MARKET_BUY_COUNT("market_buy_ad_count"), //市商可发布多少买单
    MARKET_FREEZE_COIN("market_freeze_coin"), //市商押金代币
    MARKET_FREEZE_AMOUNT("market_freeze_amount"), //市商押金数量
    ;

    private String value;

    ConfigEnums(String value) {
        this.value = value;
    }
}
