package com.blockchain.server.eth.entity;

import com.blockchain.common.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EthWalletOut 数据传输类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Table(name = "dapp_eth_wallet_out")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EthWalletOut extends BaseModel {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "addr")
	private String addr;
	@Column(name = "token_addr")
	private String tokenAddr;
	@Column(name = "token_symbol")
	private String tokenSymbol;
	@Column(name = "token_decimals")
	private Integer tokenDecimals;
	@Column(name = "private_key")
	private String privateKey;
	@Column(name = "password")
	private String password;
	@Column(name = "keystore")
	private String keystore;
	@Column(name = "remark")
	private String remark;

}