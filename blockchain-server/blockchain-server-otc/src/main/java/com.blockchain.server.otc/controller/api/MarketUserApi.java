package com.blockchain.server.otc.controller.api;

public class MarketUserApi {
    public final static String MARKET_USER_API = "市商用户控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class List {
        public static final String METHOD_TITLE_NAME = "查询市商列表";
        public static final String METHOD_TITLE_NOTE = "查询市商列表";
        public static final String METHOD_API_USER_NAME = "账户/手机号";
    }

    public static class BecomeMarketUser {
        public static final String METHOD_TITLE_NAME = "恢复用户市商身份";
        public static final String METHOD_TITLE_NOTE = "恢复用户市商身份";
        public static final String METHOD_API_ID = "用户市商记录id";
        public static final String METHOD_API_AMOUNT = "保证金数量";
        public static final String METHOD_API_COIN = "保证金代币";
    }

    public static class CancelMarketUserById {
        public static final String METHOD_TITLE_NAME = "取消用户市商身份";
        public static final String METHOD_TITLE_NOTE = "取消用户市商身份";
        public static final String METHOD_API_ID = "用户市商记录id";
    }

    public static class InsertMarketUser {
        public static final String METHOD_TITLE_NAME = "新增市商用户";
        public static final String METHOD_TITLE_NOTE = "新增市商用户";
        public static final String METHOD_API_USER_NAME = "账户";
        public static final String METHOD_API_AMOUNT = "保证金数量";
        public static final String METHOD_API_COIN = "保证金代币";
    }
}
