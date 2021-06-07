package com.blockchain.server.currency.controller.api;

public class CurrencyPairApi {
    public static final String CURRENCY_PAIR_API = "行情控制器";

    public static class listCurrencyPair {
        public static final String METHOD_TITLE_NAME = "查询行情配置列表";
        public static final String METHOD_TITLE_NOTE = "查询行情配置列表";
        public static final String METHOD_TITLE_CURRENCY_PAIR = "币对";
        public static final String METHOD_TITLE_STATUS = "状态";
        public static final String METHOD_TITLE_PAGENUM = "页码";
        public static final String METHOD_TITLE_PAGESIZE = "每页显示条数";
    }

    public static class insert {
        public static final String METHOD_TITLE_NAME = "新增行情配置列表";
        public static final String METHOD_TITLE_NOTE = "新增行情配置列表";
        public static final String METHOD_TITLE_CURRENCY_PAIR = "币对";
        public static final String METHOD_TITLE_STATUS = "状态";
        public static final String METHOD_TITLE_ORDERBY = "排序";
        public static final String METHOD_TITLE_ISHOME = "是否显示在首页";
        public static final String METHOD_TITLE_ISCCT = "是否可以币币交易";
    }

    public static class update {
        public static final String METHOD_TITLE_NAME = "更新行情配置列表";
        public static final String METHOD_TITLE_NOTE = "更新行情配置列表";
        public static final String METHOD_TITLE_CURRENCY_PAIR = "币对";
        public static final String METHOD_TITLE_STATUS = "状态";
        public static final String METHOD_TITLE_ORDERBY = "排序";
        public static final String METHOD_TITLE_ISHOME = "是否显示在首页";
        public static final String METHOD_TITLE_ISCCT = "是否可以币币交易";
    }
}
