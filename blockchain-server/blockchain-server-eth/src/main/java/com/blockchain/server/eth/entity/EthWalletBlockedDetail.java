package com.blockchain.server.eth.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author huangxl
 * @create 2019-06-25 11:26
 */
@Data
@Table(name = "dapp_eth_wallet_blocked_detail")
public class EthWalletBlockedDetail {
    @Column(name = "id")
    private String id;
    @Column(name = "wallet_uid")
    private String walletUid;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "user_open_id")
    private String userOpenId;
    @Column(name = "opt_total")
    private BigDecimal optTotal;
    @Column(name = "type")
    private String type;
    @Column(name = "system_user_id")
    private String systemUserId;
    @Column(name = "ip_addr")
    private String ipAddr;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "modify_time")
    private java.util.Date modifyTime;
}
