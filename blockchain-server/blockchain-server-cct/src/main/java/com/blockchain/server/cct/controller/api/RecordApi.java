package com.blockchain.server.cct.controller.api;

public class RecordApi {
    public static final String RECORD_API = "成交记录控制器";

    public static class listRecord {
        public static final String METHOD_TITLE_NAME = "查询成交记录详情列表";
        public static final String METHOD_TITLE_NOTE = "查询成交记录详情列表";
        public static final String METHOD_API_ORDERID = "订单id";
    }

    public static class GetById {
        public static final String METHOD_TITLE_NAME = "根据id查询";
        public static final String METHOD_TITLE_NOTE = "根据id查询";
        public static final String METHOD_API_ID = "id";
    }
}
