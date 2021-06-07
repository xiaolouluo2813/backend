package com.blockchain.server.otc.controller.api;

public class MarketApplyApi {
    public final static String MARKET_APPLY_API = "市商申请控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class list {
        public static final String METHOD_TITLE_NAME = "查询市商申请列表";
        public static final String METHOD_TITLE_NOTE = "查询市商申请列表";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
        public static final String METHOD_API_USER_NAME = "账号";
        public static final String METHOD_API_COIN_NAME = "保证金币种";
        public static final String METHOD_API_STATUS = "状态";
    }

    public class AgreeApply {
        public static final String METHOD_TITLE_NAME = "同意申请";
        public static final String METHOD_TITLE_NOTE = "同意申请";
        public static final String METHOD_API_ID = "申请记录id";
    }

    public class RejectApply {
        public static final String METHOD_TITLE_NAME = "驳回申请";
        public static final String METHOD_TITLE_NOTE = "驳回申请";
        public static final String METHOD_API_ID = "申请记录id";
    }

    public class GetById {
        public static final String METHOD_TITLE_NAME = "根据id查询申请记录";
        public static final String METHOD_TITLE_NOTE = "根据id查询申请记录";
        public static final String METHOD_API_ID = "申请记录id";
    }
}
