package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCctTradingDetail 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Table(name = "app_cct_trading_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingDetail extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "record_id")
    private String recordId;
    @Column(name = "publish_id")
    private String publishId;
    @Column(name = "total_amount")
    private String totalAmount;
    @Column(name = "real_amount")
    private String realAmount;
    @Column(name = "charge_ratio")
    private String chargeRatio;
    @Column(name = "service_charge")
    private String serviceCharge;
    @Column(name = "unit_price")
    private String unitPrice;
    @Column(name = "trading_num")
    private String tradingNum;
    @Column(name = "trading_type")
    private String tradingType;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "create_time")
    private java.util.Date createTime;

}