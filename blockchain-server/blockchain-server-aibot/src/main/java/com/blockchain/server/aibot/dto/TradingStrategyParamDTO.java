package com.blockchain.server.aibot.dto;

import lombok.Data;

/**
 * 
 * @author ljj
 * @create 2020-6-4 17:44:12
 *
 */
@Data
public class TradingStrategyParamDTO {
	private String id;
	private String coin;// 币对
	private String apiKey;
	private String secretKey;
	private String startupFunds;// 启动资金量
	private String gridNum;// 网格数
	private String spreads;// 价差比例
	private String state;// 状态,启动（Y）,禁用(N)
}
