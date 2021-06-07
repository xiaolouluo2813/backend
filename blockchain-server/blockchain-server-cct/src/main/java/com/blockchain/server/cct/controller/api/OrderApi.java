package com.blockchain.server.cct.controller.api;

public class OrderApi {
    public static final String ORDER_API = "发布订单控制器";

    public static class listMatch {
        public static final String METHOD_TITLE_NAME = "查询挂单列表";
        public static final String METHOD_TITLE_NOTE = "查询挂单列表";
        public static final String METHOD_API_PARAM = "查询参数DTO";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }

    public static class listFinish {
        public static final String METHOD_TITLE_NAME = "查询历史订单列表";
        public static final String METHOD_TITLE_NOTE = "查询历史订单列表";
        public static final String METHOD_API_PARAM = "查询参数DTO";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "每页显示条数";
    }

    public static class cancel {
        public static final String METHOD_TITLE_NAME = "撤销订单";
        public static final String METHOD_TITLE_NOTE = "撤销订单";
        public static final String METHOD_API_ORDERID = "订单id";
    }
}
