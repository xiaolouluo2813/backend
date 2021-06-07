package com.blockchain.server.eos.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Wallet 数据传输类
 * @date 2018-11-05 15:10:47
 * @version 1.0
 */
@Table(name = "dapp_eos_wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet extends BaseModel {
	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "token_name")
	private String tokenName;
	@Column(name = "user_open_id")
	private String userOpenId;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "balance")
	private BigDecimal balance;
	@Column(name = "free_balance")
	private BigDecimal freeBalance;
	@Column(name = "freeze_balance")
	private BigDecimal freezeBalance;
	@Column(name = "create_time")
	private java.util.Date createTime;
	@Column(name = "update_time")
	private java.util.Date updateTime;
	@Column(name = "wallet_type")
	private String walletType;

}