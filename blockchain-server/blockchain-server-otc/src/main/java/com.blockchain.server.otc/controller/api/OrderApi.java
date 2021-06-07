package com.blockchain.server.otc.controller.api;

public class OrderApi {
    public final static String ORDER_API = "订单控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listOrder {
        public static final String METHOD_TITLE_NAME = "查询订单列表";
        public static final String METHOD_TITLE_NOTE = "查询订单列表";
        public static final String METHOD_API_PARAM_DTO = "查询参数";
    }

    public static class selectByOrderNumber {
        public static final String METHOD_TITLE_NAME = "根据订单流水号查询订单信息";
        public static final String METHOD_TITLE_NOTE = "根据订单流水号查询订单信息";
        public static final String METHOD_API_ORDER_NUMBER = "订单流水号";
    }

    public static class selectByAdId {
        public static final String METHOD_TITLE_NAME = "根据广告id查询订单";
        public static final String METHOD_TITLE_NOTE = "根据广告id查询订单";
        public static final String METHOD_API_AD_ID = "广告id";
    }

    public static class selectById {
        public static final String METHOD_TITLE_NAME = "根据id查询订单";
        public static final String METHOD_TITLE_NOTE = "根据id查询订单";
        public static final String METHOD_API_ID = "订单id";
    }
}
