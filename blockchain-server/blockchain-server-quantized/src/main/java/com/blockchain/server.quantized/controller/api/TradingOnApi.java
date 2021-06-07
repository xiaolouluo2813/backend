package com.blockchain.server.quantized.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:51
 **/
public class TradingOnApi {

    public static final String CONTROLLER_API = "交易对控制器";

    public static class List {
        public static final String METHOD_TITLE_NAME = "系统用户列表接口";
        public static final String METHOD_TITLE_NOTE = "系统用户列表接口，可根据状态查询";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
        public static final String METHOD_API_MOBILEPHONE = "手机号";
        public static final String METHOD_API_STATE = "订单状态";
        public static final String METHOD_API_TYPE = "订单类型";
    }

    public static class Delete {
        public static final String METHOD_TITLE_NAME = "删除用户接口";
        public static final String METHOD_TITLE_NOTE = "删除用户";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
    }
    public static class Add {
        public static final String METHOD_TITLE_NAME = "添加接口";
        public static final String METHOD_TITLE_NOTE = "添加接口";
        public static final String METHOD_API_COINNAME = "基本货币";
        public static final String METHOD_API_UNITNAME = "二级货币";
        public static final String METHOD_API_STATE = "是否开启（Y是，N否）";
    }
    public static class UpdateState {
        public static final String METHOD_TITLE_NAME = "修改状态接口";
        public static final String METHOD_TITLE_NOTE = "修改状态接口";
        public static final String METHOD_API_ID = "id";
        public static final String METHOD_API_STATE = "是否开启（Y是，N否）";
    }
}
