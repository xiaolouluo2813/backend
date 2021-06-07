package com.blockchain.server.cct.controller.api;

public class AutomaticdataApi {
    public static final String AUTOMATICDATA_API = "自动盘口数据规则控制器";

    public static class list {
        public static final String METHOD_TITLE_NAME = "查询盘口数据规则列表";
        public static final String METHOD_TITLE_NOTE = "查询盘口数据规则列表";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }

    public static class insert {
        public static final String METHOD_TITLE_NAME = "新增盘口数据规则";
        public static final String METHOD_TITLE_NOTE = "新增盘口数据规则";
        public static final String METHOD_API_PARAM = "新增参数";
    }

    public static class delete {
        public static final String METHOD_TITLE_NAME = "删除盘口数据规则";
        public static final String METHOD_TITLE_NOTE = "删除盘口数据规则";
        public static final String METHOD_API_ID = "盘口数据规则Id";
    }

    public static class update {
        public static final String METHOD_TITLE_NAME = "新增盘口数据规则列表";
        public static final String METHOD_TITLE_NOTE = "新增盘口数据规则列表";
        public static final String METHOD_API_PARAM = "新增参数";
    }
}
