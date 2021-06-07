package com.blockchain.server.otc.controller.api;

public class UserPayApi {
    public final static String USER_PAY_API = "用户支付信息控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listUserPayInfo {
        public static final String METHOD_TITLE_NAME = "查询用户支付信息列表";
        public static final String METHOD_TITLE_NOTE = "查询用户支付信息列表";
        public static final String METHOD_API_USERNAME = "账户/手机号";
        public static final String METHOD_API_PAYTYPE = "支付类型";
    }

}
