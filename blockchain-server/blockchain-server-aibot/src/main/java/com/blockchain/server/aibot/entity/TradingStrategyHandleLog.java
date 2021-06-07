package com.blockchain.server.aibot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "pc_trading_strategy_handle_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingStrategyHandleLog {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "handle_type")
	private String handleType;
	@Column(name = "sys_user_id")
	private String sysUserId;
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "before_coin")
	private String beforeCoin;
	@Column(name = "after_coin")
	private String afterCoin;
	@Column(name = "before_api_key")
	private String beforeApiKey;
	@Column(name = "after_api_key")
	private String afterApiKey;
	@Column(name = "before_secret_key")
	private String beforeSecretKey;
	@Column(name = "after_secret_key")
	private String afterSecretKey;
	@Column(name = "before_startup_funds")
	private String beforeStartupFunds;
	@Column(name = "after_startup_funds")
	private String afterStartupFunds;
	@Column(name = "before_grid_num")
	private String beforeGridNum;
	@Column(name = "after_grid_num")
	private String afterGridNum;
	@Column(name = "before_spreads")
	private String beforeSpreads;
	@Column(name = "after_spreads")
	private String afterSpreads;
	@Column(name = "before_state")
	private String beforeState;
	@Column(name = "after_state")
	private String afterState;
	@Column(name = "create_time")
	private Date createTime;
}
