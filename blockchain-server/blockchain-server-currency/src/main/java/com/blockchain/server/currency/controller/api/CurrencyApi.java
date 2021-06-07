package com.blockchain.server.currency.controller.api;

public class CurrencyApi {
    public static final String CURRENCY_API = "币种控制器";

    public static class listCurrency {
        public static final String METHOD_TITLE_NAME = "查询币种列表";
        public static final String METHOD_TITLE_NOTE = "查询币种列表";
        public static final String METHOD_TITLE_COINNAME = "币种名";
        public static final String METHOD_TITLE_STATUS = "状态";
        public static final String METHOD_TITLE_PAGENUM = "页码";
        public static final String METHOD_TITLE_PAGESIZE = "每页显示条数";
    }

    public static class insert {
        public static final String METHOD_TITLE_NAME = "新增币种";
        public static final String METHOD_TITLE_NOTE = "新增币种";
    }

    public static class update {
        public static final String METHOD_TITLE_NAME = "更新币种";
        public static final String METHOD_TITLE_NOTE = "更新币种";
    }
}
