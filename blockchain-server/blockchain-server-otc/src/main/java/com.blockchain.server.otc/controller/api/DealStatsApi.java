package com.blockchain.server.otc.controller.api;

public class DealStatsApi {
    public final static String DEAL_STATS_API = "用户交易统计控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listDealStats {
        public static final String METHOD_TITLE_NAME = "查询用户成交统计列表";
        public static final String METHOD_TITLE_NOTE = "查询用户成交统计列表";
        public static final String METHOD_API_USERNAME = "账户/手机号";
    }
}
