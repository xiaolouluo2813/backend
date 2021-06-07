package com.blockchain.server.eos.controller.api;

/**
 * @author huangxl
 * @create 2019-06-25 11:11
 */
public class EosBlockApi {

    public static class Block {
        public static final String METHOD_TITLE_NAME = "冻结余额";
        public static final String METHOD_TITLE_NOTE = "冻结余额";
        public static final String METHOD_API_USER_OPEN_ID = "用户id";
        public static final String METHOD_API_WALLET_UID = "钱包id";
        public static final String METHOD_API_TOKEN_SYMBOL = "代币名称";
        public static final String METHOD_API_OPT_NUMBER = "冻结数量";
        public static final String METHOD_API_REMARK = "说明";
    }

    public static class UnBlock {
        public static final String METHOD_TITLE_NAME = "解冻余额";
        public static final String METHOD_TITLE_NOTE = "解冻余额";
        public static final String METHOD_API_USER_OPEN_ID = "用户id";
        public static final String METHOD_API_WALLET_UID = "钱包id";
        public static final String METHOD_API_TOKEN_SYMBOL = "代币名称";
        public static final String METHOD_API_OPT_NUMBER = "解冻数量";
        public static final String METHOD_API_REMARK = "说明";
    }

    public static class Details {
        public static final String METHOD_TITLE_NAME = "查询冻结解冻记录";
        public static final String METHOD_TITLE_NOTE = "查询冻结解冻记录";
        public static final String METHOD_API_USERNAME = "用户账号";
        public static final String METHOD_API_TYPE = "类型";
    }

    public static class SelectTotal {
        public static final String METHOD_TITLE_NAME = "查询冻结解冻总记录";
        public static final String METHOD_TITLE_NOTE = "查询冻结解冻总记录";
        public static final String METHOD_API_WALLET_UID = "钱包id";
        public static final String METHOD_API_TOKEN_SYMBOL = "代币名称";
    }
}
