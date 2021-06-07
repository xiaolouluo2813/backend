package com.blockchain.server.currency.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Currency 数据传输类
 *
 * @version 1.0
 * @date 2019-03-14 15:06:53
 */
@Table(name = "dapp_currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency extends BaseModel {
    @Id
    @Column(name = "currency_name")
    private String currencyName;
    @Column(name = "currency_name_cn")
    private String currencyNameCn;
    @Column(name = "currency_name_hk")
    private String currencyNameHk;
    @Column(name = "currency_name_en")
    private String currencyNameEn;
    @Column(name = "status")
    private Integer status;
    @Column(name = "order_by")
    private Integer orderBy;
    @Column(name = "issue_time")
    private String issueTime;
    @Column(name = "total_supply")
    private String totalSupply;
    @Column(name = "total_circulation")
    private String totalCirculation;
    @Column(name = "ico_amount")
    private String icoAmount;
    @Column(name = "white_paper")
    private String whitePaper;
    @Column(name = "official_website")
    private String officialWebsite;
    @Column(name = "block_url")
    private String blockUrl;
    @Column(name = "descr_cn")
    private String descrCn;
    @Column(name = "descr_en")
    private String descrEn;
    @Column(name = "descr_hk")
    private String descrHk;

}