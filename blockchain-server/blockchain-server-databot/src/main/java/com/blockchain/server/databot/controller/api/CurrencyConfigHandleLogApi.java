package com.blockchain.server.databot.controller.api;

public class CurrencyConfigHandleLogApi {
    public static final String CURRENCY_CONFIG_HANDLE_LOG_API = "币对配置操作日志控制器";

    public static class list {
        public static final String METHOD_TITLE_NAME = "查询币对配置操作日志列表";
        public static final String METHOD_TITLE_NOTE = "查询币对配置操作日志列表";
        public static final String METHOD_API_CURRENCY_PAIR = "币对";
        public static final String METHOD_API_HANDLE_TYPE = "操作类型";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }
}
