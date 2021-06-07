package com.blockchain.server.databot.controller.api;

public class MatchConfigApi {
    public static final String MATCH_CONFIG_API = "撮合配置控制器";

    public static class list {
        public static final String METHOD_TITLE_NAME = "查询撮合配置信息列表";
        public static final String METHOD_TITLE_NOTE = "查询撮合配置信息列表";
        public static final String METHOD_API_USERNAME = "账户";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_STATUS = "状态";
        public static final String METHOD_API_PRICE_TYPE = "撮合单价规则类型";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class insert {
        public static final String METHOD_TITLE_NAME = "新增撮合配置信息";
        public static final String METHOD_TITLE_NOTE = "新增撮合配置信息";
        public static final String METHOD_API_PARAM_DTO = "新增参数";
    }

    public static class update {
        public static final String METHOD_TITLE_NAME = "更新撮合配置信息";
        public static final String METHOD_TITLE_NOTE = "更新撮合配置信息";
        public static final String METHOD_API_PARAM_DTO = "更新参数";
    }

    public static class delete {
        public static final String METHOD_TITLE_NAME = "删除撮合配置信息";
        public static final String METHOD_TITLE_NOTE = "删除撮合配置信息";
        public static final String METHOD_API_ID = "配置Id";
    }
}
