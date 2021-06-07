package com.blockchain.server.otc.controller.api;

public class ConfigApi {
    public final static String CONFIG_API = "配置信息控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listConfig {
        public static final String METHOD_TITLE_NAME = "查询配置信息列表";
        public static final String METHOD_TITLE_NOTE = "查询配置信息列表";
        public static final String METHOD_API_DATA_KEY = "配置名";
    }

    public static class updateConfig {
        public static final String METHOD_TITLE_NAME = "更新配置信息";
        public static final String METHOD_TITLE_NOTE = "更新配置信息";
        public static final String METHOD_API_ID = "配置信息ID";
        public static final String METHOD_API_DATA_VALUE = "配置信息的值";
        public static final String METHOD_API_STATUS = "配置信息状态";
    }
}
