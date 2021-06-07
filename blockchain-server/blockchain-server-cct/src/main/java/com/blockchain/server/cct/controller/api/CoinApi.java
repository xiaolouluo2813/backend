package com.blockchain.server.cct.controller.api;

public class CoinApi {
    public static final String COIN_API = "交易对配置控制器";

    public static class listCoin {
        public static final String METHOD_TITLE_NAME = "查询交易对列表";
        public static final String METHOD_TITLE_NOTE = "查询交易对列表";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_STATUS = "币对状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static class updateCoin {
        public static final String METHOD_TITLE_NAME = "更新交易对信息";
        public static final String METHOD_TITLE_NOTE = "更新交易对信息";
        public static final String METHOD_API_PARAM = "更新参数";
    }

    public static class insertCoin {
        public static final String METHOD_TITLE_NAME = "新增交易对信息";
        public static final String METHOD_TITLE_NOTE = "新增交易对信息";
        public static final String METHOD_API_PARAM = "更新参数";
    }
}
