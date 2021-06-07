package com.blockchain.server.btc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EthClearingCountTotal 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Table(name = "dapp_btc_clearing_count_total")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcClearingCountTotal extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "real_balance")
    private BigDecimal realBalance;
    @Column(name = "diff_balance")
    private BigDecimal diffBalance;
    @Column(name = "status")
    private String status;
    @Column(name = "pre_time")
    private Date preTime;
    @Column(name = "pre_balance")
    private BigDecimal preBalance;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "modify_time")
    private Date modifyTime;


}