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
 * EthClearingCorr 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Table(name = "dapp_btc_clearing_corr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcClearingCorr extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "total_id")
    private String totalId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "wallet_type")
    private String walletType;
    @Column(name = "pre_balance")
    private BigDecimal preBalance;
    @Column(name = "after_balance")
    private BigDecimal afterBalance;
    @Column(name = "system_user_id")
    private String systemUserId;
    @Column(name = "clearing_time")
    private Date clearingTime;
    @Column(name = "create_time")
    private Date createTime;


}