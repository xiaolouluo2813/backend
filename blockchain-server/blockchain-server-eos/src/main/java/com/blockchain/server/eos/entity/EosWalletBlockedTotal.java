package com.blockchain.server.eos.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author huangxl
 * @create 2019-06-25 11:26
 */
@Data
@Table(name = "dapp_eos_wallet_blocked_total")
public class EosWalletBlockedTotal {
    @Column(name = "id")
    private String id;
    @Column(name = "wallet_uid")
    private String walletUid;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "user_open_id")
    private String userOpenId;
    @Column(name = "blocked_total")
    private BigDecimal blockedTotal;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;
}
