package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AppCctTradingRecord 数据传输类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Table(name = "app_cct_trading_record")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingRecord extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "maker_id")
    private String makerId;
    @Column(name = "taker_id")
    private String takerId;
    @Column(name = "maker_price")
    private String makerPrice;
    @Column(name = "taker_price")
    private String takerPrice;
    @Column(name = "trading_num")
    private String tradingNum;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "create_time")
    private java.util.Date createTime;

}