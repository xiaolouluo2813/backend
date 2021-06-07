package com.blockchain.server.otc.controller.api;

public class AdHandleLogApi {
    public final static String AD_HANDLE_LOG_API = "广告操作日志控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listAdHandleLog {
        public static final String METHOD_TITLE_NAME = "查询广告操作日志列表";
        public static final String METHOD_TITLE_NOTE = "查询广告操作日志列表";
        public static final String METHOD_API_AD_NUMBER = "广告流水号";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }
}
