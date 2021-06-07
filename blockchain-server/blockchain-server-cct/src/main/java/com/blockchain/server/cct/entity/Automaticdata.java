package com.blockchain.server.cct.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Automaticdata 数据传输类
 *
 * @version 1.0
 * @date 2019-04-08 13:55:48
 */
@Table(name = "app_cct_automaticdata")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Automaticdata extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "coin_name")
    private String coinName;
    @Column(name = "unit_name")
    private String unitName;
    @Column(name = "min_price")
    private Float minPrice;
    @Column(name = "max_price")
    private Float maxPrice;
    @Column(name = "min_amount")
    private Float minAmount;
    @Column(name = "max_amount")
    private Float maxAmount;
    @Column(name = "order_type")
    private String orderType;
}