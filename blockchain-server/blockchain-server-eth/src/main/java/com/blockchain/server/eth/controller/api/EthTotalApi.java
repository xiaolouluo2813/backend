package com.blockchain.server.eth.controller.api;

public class EthTotalApi {
    public static final String API_NAME = "以太坊充值记录控制器";

    public static class All {
        public static final String METHOD_TITLE_NAME = "查询所有统计记录的详情信息";
        public static final String METHOD_TITLE_NOTE = "查询所有统计记录的详情信息";
        public static final String METHOD_API_USERID = "用户ID";
    }

    public static class Save {
        public static final String METHOD_TITLE_NAME = "查询所有统计记录的详情信息";
        public static final String METHOD_TITLE_NOTE = "查询所有统计记录的详情信息";
        public static final String METHOD_API_TOTALID = "统计记录的标识";
        public static final String METHOD_API_AMOUNT = "更正后的资金";
    }

    public static class Total {
        public static final String METHOD_TITLE_NAME = "统计当前的详情信息";
        public static final String METHOD_TITLE_NOTE = "统计当前的详情信息";
        public static final String METHOD_API_USERID = "统计记录的标识";
    }
}