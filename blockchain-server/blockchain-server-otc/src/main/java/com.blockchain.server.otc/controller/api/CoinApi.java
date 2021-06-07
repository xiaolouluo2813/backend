package com.blockchain.server.otc.controller.api;

public class CoinApi {
    public final static String COIN_API = "币种控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listCoin {
        public static final String METHOD_TITLE_NAME = "查询币种列表";
        public static final String METHOD_TITLE_NOTE = "查询币种列表";
        public static final String METHOD_API_COIN_NAME = "币种/基本货币";
        public static final String METHOD_API_UNIT_NAME = "单位/二级货币";
    }

    public static class updateCoin {
        public static final String METHOD_TITLE_NAME = "更新币种";
        public static final String METHOD_TITLE_NOTE = "更新币种";
        public static final String METHOD_API_PARAM_DTO = "更新参数";
    }
}
