package com.blockchain.server.otc.controller.api;

public class UserHandleLogApi {
    public final static String USER_HANDLE_LOG_API = "用户操作日志控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listUserHandleLog {
        public static final String METHOD_TITLE_NAME = "查询用户支付信息列表";
        public static final String METHOD_TITLE_NOTE = "查询用户支付信息列表";
        public static final String METHOD_API_USERNAME = "账户/手机号";
        public static final String METHOD_API_HANDLE_NUMBER = "操作的订单/广告流水号";
        public static final String METHOD_API_HANDLE_NUMBER_TYPE = "操作的记录类型-订单/广告";
        public static final String METHOD_API_HANDLE_TYPE = "操作类型";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }
}
