package com.blockchain.server.aibot.controller.api;

public class TradingStrategyApi {
	public static final String TRADING_STRATEGY_ACCOUNT_API = "交易策略控制器";

	public static class list {
		public static final String METHOD_TITLE_NAME = "查询交易策略列表";
		public static final String METHOD_TITLE_NOTE = "查询交易策略列表";
		public static final String METHOD_API_COIN = "币对";
		public static final String METHOD_API_STATE = "状态";
		public static final String METHOD_API_PAGENUM = "分页页码";
		public static final String METHOD_API_PAGESIZE = "分页每页显示条数";
	}

	public static class insert {
		public static final String METHOD_TITLE_NAME = "新增交易策略信息";
		public static final String METHOD_TITLE_NOTE = "新增交易策略信息";
		public static final String METHOD_API_PARAM = "参数";
	}

	public static class update {
		public static final String METHOD_TITLE_NAME = "更新交易策略信息";
		public static final String METHOD_TITLE_NOTE = "更新交易策略信息";
		public static final String METHOD_API_PARAM = "参数";
	}

	public static class tradingStrategy {
		public static final String METHOD_TITLE_NAME = "(开启/关闭)交易策略";
		public static final String METHOD_TITLE_NOTE = "(开启/关闭)交易策略";
		public static final String METHOD_API_ID = "交易策略ID";
		public static final String METHOD_API_STATUS = "交易策略状态";
	}
}
