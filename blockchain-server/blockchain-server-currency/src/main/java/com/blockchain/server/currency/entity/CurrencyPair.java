package com.blockchain.server.currency.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CurrencyPair 数据传输类
 *
 * @version 1.0
 * @date 2019-03-14 15:06:53
 */
@Table(name = "dapp_currency_pair")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPair extends BaseModel {
    @Id
    @Column(name = "currency_pair")
    private String currencyPair;
    @Column(name = "status")
    private Integer status;
    @Column(name = "order_by")
    private Integer orderBy;
    @Column(name = "is_home")
    private Integer isHome;
    @Column(name = "is_cct")
    private Integer isCct;
}