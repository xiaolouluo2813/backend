package com.blockchain.server.aibot.dto;

import lombok.Data;

@Data
public class TradingStrategyHandleLogResultDTO {
	private String id;
	private String handleType;
	private String sysUserId;
	private String ipAddress;
	private String beforeCoin;
	private String afterCoin;
	private String beforeApiKey;
	private String afterApiKey;
	private String beforeSecretKey;
	private String afterSecretKey;
	private String beforeStartupFunds;
	private String afterStartupFunds;
	private String beforeGridNum;
	private String afterGridNum;
	private String beforeSpreads;
	private String afterSpreads;
	private String beforeState;
	private String afterState;
	private java.util.Date createTime;

}
