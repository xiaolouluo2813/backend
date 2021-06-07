package com.blockchain.server.eth.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-27 09:26
 **/
public class EthWalletOutApi {

    public static final String CONTROLLER_API = "发币账户管理控制器";

    public static class List {

        public static final String METHOD_TITLE_NAME = "系统用户列表接口";
        public static final String METHOD_TITLE_NOTE = "系统用户列表接口，可根据状态查询";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class Insert {
        public static final String METHOD_TITLE_NAME = "新增系统用户接口";
        public static final String METHOD_TITLE_NOTE = "系统用户新增接口";
        public static final String METHOD_API_ADDR = "账户名称";
        public static final String METHOD_API_TOKENID = "币种id";
        public static final String METHOD_API_REMARK = "钱包备注";
        public static final String METHOD_API_TOKENDECIMALS = "币种小数";
        public static final String METHOD_API_PRIVATEKEY = "资金账户私钥";
    }

    public static class Delete {
        public static final String METHOD_TITLE_NAME = "删除用户接口";
        public static final String METHOD_TITLE_NOTE = "删除用户";
        public static final String METHOD_API_ID = "账户id";
    }
}
