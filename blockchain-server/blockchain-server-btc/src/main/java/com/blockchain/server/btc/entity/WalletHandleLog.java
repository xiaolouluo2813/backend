package com.blockchain.server.btc.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BtcWalletTransferDTO 数据传输类
 *
 * @version 1.0
 * @date 2019-02-16 15:08:16
 */
@Table(name = "wallet_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletHandleLog extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "account")
    private String account;
    @Column(name = "username")
    private String username;
    @Column(name = "data_key")
    private String dataKey;
    @Column(name = "before_satus")
    private String beforeSatus;
    @Column(name = "after_satus")
    private String afterSatus;
    @Column(name = "wallet_addr")
    private String walletAddr;
    @Column(name = "amount")
    private String amount;
    @Column(name = "type")
    private String type;
    @Column(name = "create_time")
    private java.util.Date createTime;
}