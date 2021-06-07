package com.blockchain.server.cct.controller.api;

public class CommissionApi {
    public static final String COMMISSION_API = "佣金控制器";

    public static final class List {
        public static final String METHOD_TITLE_NAME = "查询佣金列表";
        public static final String METHOD_TITLE_NOTE = "查询佣金列表";
        public static final String METHOD_API_USER_NAME = "账户";
        public static final String METHOD_API_P_USER_NAME = "父级账户";
        public static final String METHOD_API_COINNAME = "币种";
        public static final String METHOD_API_STATUS = "状态";
        public static final String METHOD_API_PAGENUM = "页码";
        public static final String METHOD_API_PAGESIZE = "分页条数";
    }

    public static final class IssueCommission {
        public static final String METHOD_TITLE_NAME = "发放佣金";
        public static final String METHOD_TITLE_NOTE = "发放佣金";
    }
}
