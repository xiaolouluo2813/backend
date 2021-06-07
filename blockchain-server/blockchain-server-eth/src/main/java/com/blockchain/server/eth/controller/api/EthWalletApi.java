package com.blockchain.server.eth.controller.api;

public class EthWalletApi {
    public static final String API_NAME = "以太坊钱包控制器";
    public static final String METHOD_API_PARAM = "条件参数";
    public static final String METHOD_API_PAGESIZE = "分页每页显示条数";
    public static final String METHOD_API_PAGENUM = "分页页码";
    public static final String METHOD_API_ADDR = "钱包地址";
    public static final String METHOD_API_COINNAME = "币种名称";
    public static final String METHOD_API_FREEBLANCE = "可用余额";
    public static final String METHOD_API_FREEZEBLANCE = "冻结余额";

    public static class CCT {
        public static final String METHOD_TITLE_NAME = "查询所有CCT钱包详情信息";
        public static final String METHOD_TITLE_NOTE = "查询所有CCT钱包详情信息";
    }
    public static class All {
        public static final String METHOD_TITLE_NAME = "查询所有钱包详情信息";
        public static final String METHOD_TITLE_NOTE = "查询所有钱包详情信息";
    }
    public static class Add {
        public static final String METHOD_TITLE_NAME = "增加余额";
        public static final String METHOD_TITLE_NOTE = "增加余额";
    }
    public static class Sud {
        public static final String METHOD_TITLE_NAME = "减少余额";
        public static final String METHOD_TITLE_NOTE = "减少余额";
    }

}
