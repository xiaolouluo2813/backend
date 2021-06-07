package com.blockchain.server.databot.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CurrencyConfigHandleLog 数据传输类
 *
 * @version 1.0
 * @date 2019-06-04 10:03:37
 */
@Table(name = "bot_currency_config_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConfigHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "currency_pair")
    private String currencyPair;
    @Column(name = "handle_type")
    private String handleType;
    @Column(name = "sys_user_id")
    private String sysUserId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "before_k_change_percent")
    private Float beforeKChangePercent;
    @Column(name = "after_k_change_percent")
    private Float afterKChangePercent;
    @Column(name = "before_k_max_change_percent")
    private Float beforeKMaxChangePercent;
    @Column(name = "after_k_max_change_percent")
    private Float afterKMaxChangePercent;
    @Column(name = "before_k_day_total_amount")
    private Float beforeKDayTotalAmount;
    @Column(name = "after_k_day_total_amount")
    private Float afterKDayTotalAmount;
    @Column(name = "before_buy_price_percent")
    private Float beforeBuyPricePercent;
    @Column(name = "after_buy_price_percent")
    private Float afterBuyPricePercent;
    @Column(name = "before_buy_total_amount")
    private Float beforeBuyTotalAmount;
    @Column(name = "after_buy_total_amount")
    private Float afterBuyTotalAmount;
    @Column(name = "before_sell_price_percent")
    private Float beforeSellPricePercent;
    @Column(name = "after_sell_price_percent")
    private Float afterSellPricePercent;
    @Column(name = "before_sell_total_amount")
    private Float beforeSellTotalAmount;
    @Column(name = "after_sell_total_amount")
    private Float afterSellTotalAmount;
    @Column(name = "before_k_max_price")
    private Float beforeKMaxPrice;
    @Column(name = "after_k_max_price")
    private Float afterKMaxPrice;
    @Column(name = "before_k_min_price")
    private Float beforeKMinPrice;
    @Column(name = "after_k_min_price")
    private Float afterKMinPrice;
    @Column(name = "before_buy_max_price")
    private Float beforeBuyMaxPrice;
    @Column(name = "after_buy_max_price")
    private Float afterBuyMaxPrice;
    @Column(name = "before_buy_min_price")
    private Float beforeBuyMinPrice;
    @Column(name = "after_buy_min_price")
    private Float afterBuyMinPrice;
    @Column(name = "before_sell_max_price")
    private Float beforeSellMaxPrice;
    @Column(name = "after_sell_max_price")
    private Float afterSellMaxPrice;
    @Column(name = "before_sell_min_price")
    private Float beforeSellMinPrice;
    @Column(name = "after_sell_min_price")
    private Float afterSellMinPrice;
    @Column(name = "before_price_type")
    private String beforePriceType;
    @Column(name = "after_price_type")
    private String afterPriceType;
    @Column(name = "create_time")
    private java.util.Date createTime;
}