package com.blockchain.server.eos.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-26 16:23
 **/
public class EosWalletOutApi {

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
        public static final String METHOD_API_ACCOUNTNAME = "账户名称";
        public static final String METHOD_API_TOKENNAME = "币种id";
        public static final String METHOD_API_REMARK = "钱包备注";
        public static final String METHOD_API_STATUS = "显示状态： 显示(Y)，隐藏(N)";
        public static final String METHOD_API_PRIVATEKEY = "资金账户私钥";
    }

    public static class Update {
        public static final String METHOD_TITLE_NAME = "修改系统用户基本信息接口";
        public static final String METHOD_TITLE_NOTE = "系统用户修改信息接口";
        public static final String METHOD_API_ACCOUNTNAME = "账户名称";
        public static final String METHOD_API_TOKENNAME = "币种id";
        public static final String METHOD_API_REMARK = "钱包备注";
        public static final String METHOD_API_STATUS = "显示状态： 显示(Y)，隐藏(N)";
        public static final String METHOD_API_ID = "账户id";
    }

    public static class Delete {
        public static final String METHOD_TITLE_NAME = "删除用户接口";
        public static final String METHOD_TITLE_NOTE = "删除用户";
        public static final String METHOD_API_ID = "账户id";
    }
}
