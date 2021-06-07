package com.blockchain.server.otc.controller.api;

public class BillApi {
    public final static String BILL_API = "资金记录控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listBill {
        public static final String METHOD_TITLE_NAME = "查询资金变动列表";
        public static final String METHOD_TITLE_NOTE = "查询资金变动列表";
        public static final String METHOD_API_USER_NAME = "账户/手机号";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }
}
