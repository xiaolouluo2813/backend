package com.blockchain.server.aibot.controller.api;

public class TradingStrategyHandleLogApi {
	public static final String TRADING_STRATEGY_HANDLE_LOG_API = "交易策略操作日志控制器";

	public static class list {
		public static final String METHOD_TITLE_NAME = "查询交易策略操作日志列表";
		public static final String METHOD_TITLE_NOTE = "查询交易策略操作日志列表";
        public static final String METHOD_API_HANDLE_TYPE = "操作类型";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
		public static final String METHOD_API_PAGENUM = "页码";
		public static final String METHOD_API_PAGESIZE = "分页条数";
	}
}
