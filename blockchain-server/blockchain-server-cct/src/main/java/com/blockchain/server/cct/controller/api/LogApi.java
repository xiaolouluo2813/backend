package com.blockchain.server.cct.controller.api;

public class LogApi {
    public static final String LOG_API = "币币后台操作日志控制器";

    public static class listCoinLog {
        public static final String METHOD_TITLE_NAME = "查询交易对日志列表";
        public static final String METHOD_TITLE_NOTE = "查询交易对日志列表";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }

    public static class listOrderLog {
        public static final String METHOD_TITLE_NAME = "查询订单日志列表";
        public static final String METHOD_TITLE_NOTE = "查询订单日志列表";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }

    public static class listConfigLog {
        public static final String METHOD_TITLE_NAME = "查询配置日志列表";
        public static final String METHOD_TITLE_NOTE = "查询配置日志列表";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }
}
