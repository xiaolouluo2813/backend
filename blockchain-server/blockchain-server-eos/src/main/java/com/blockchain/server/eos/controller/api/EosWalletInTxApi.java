package com.blockchain.server.eos.controller.api;

public class EosWalletInTxApi {
    public static final String API_NAME = "以太坊充值记录控制器";
    public static final String METHOD_API_PARAM = "条件参数";
    public static final String METHOD_API_PAGESIZE = "分页每页显示条数";
    public static final String METHOD_API_PAGENUM = "分页页码";

    public static class Success {
        public static final String METHOD_TITLE_NAME = "查询充值成功的记录";
        public static final String METHOD_TITLE_NOTE = "查询充值成功的记录";
    }

    public static class Error {
        public static final String METHOD_TITLE_NAME = "查询充值失败的记录";
        public static final String METHOD_TITLE_NOTE = "查询充值失败的记录";
    }

    public static class All {
        public static final String METHOD_TITLE_NAME = "查询所有充值的记录";
        public static final String METHOD_TITLE_NOTE = "查询所有充值的记录";
    }

    public static class selectById {
        public static final String METHOD_TITLE_NAME = "根据id查询充值的记录";
        public static final String METHOD_TITLE_NOTE = "根据id查询充值的记录";
        public static final String METHOD_API_ID = "记录id";
    }
}
