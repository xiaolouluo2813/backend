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
@Table(name = "dapp_btc_wallet_transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BtcWalletTransfer extends BaseModel {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "hash")
    private String hash;
    @Column(name = "from_addr")
    private String fromAddr;
    @Column(name = "to_addr")
    private String toAddr;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "token_id")
    private Integer tokenId;
    @Column(name = "token_symbol")
    private String tokenSymbol;
    @Column(name = "gas_price")
    private Double gasPrice;
    @Column(name = "gas_token_type")
    private String gasTokenType;
    @Column(name = "gas_token_name")
    private String gasTokenName;
    @Column(name = "gas_token_symbol")
    private String gasTokenSymbol;
    @Column(name = "transfer_type")
    private String transferType;
    @Column(name = "status")
    private Integer status;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private java.util.Date createTime;
    @Column(name = "update_time")
    private java.util.Date updateTime;

}