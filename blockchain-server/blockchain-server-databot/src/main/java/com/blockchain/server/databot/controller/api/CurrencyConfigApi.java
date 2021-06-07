package com.blockchain.server.databot.controller.api;

public class CurrencyConfigApi {
    public static final String CURRENCY_CONFIG_API = "币对配置控制器";

    public static class list {
        public static final String METHOD_TITLE_NAME = "查询币对配置信息列表";
        public static final String METHOD_TITLE_NOTE = "查询币对配置信息列表";
        public static final String METHOD_API_CURRENCY_PAIR = "币对";
        public static final String METHOD_API_STATUS = "状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class updateConfig {
        public static final String METHOD_TITLE_NAME = "更新币对配置信息";
        public static final String METHOD_TITLE_NOTE = "更新币对配置信息";
        public static final String METHOD_API_PARAM_DTO = "更新参数";
    }

    public static class insertConfig {
        public static final String METHOD_TITLE_NAME = "新增币对配置信息";
        public static final String METHOD_TITLE_NOTE = "新增币对配置信息";
        public static final String METHOD_API_PARAM_DTO = "新增参数";
    }
}
