package com.blockchain.server.otc.controller.api;

public class AppealApi {
    public final static String APPEAL_DETAIL_API = "申诉控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listAppeal {
        public static final String METHOD_TITLE_NAME = "查询申诉列表";
        public static final String METHOD_TITLE_NOTE = "查询申诉列表";
        public static final String METHOD_API_ORDER_NUMBER = "订单流水号";
        public static final String METHOD_API_STATUS = "申诉记录状态";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }

    public static class handleFinishOrder {
        public static final String METHOD_TITLE_NAME = "处理订单为已完成";
        public static final String METHOD_TITLE_NOTE = "处理订单为已完成";
        public static final String METHOD_API_APPEAL_ID = "申诉记录id";
        public static final String METHOD_API_REMARK = "管理员处理备注";
    }

    public static class handleCancelOrder {
        public static final String METHOD_TITLE_NAME = "处理订单为已失效";
        public static final String METHOD_TITLE_NOTE = "处理订单为已失效";
        public static final String METHOD_API_APPEAL_ID = "申诉记录id";
        public static final String METHOD_API_REMARK = "管理员处理备注";
    }
}
