package com.blockchain.server.eos.controller.api;

public class EosWalletOutTxApi {
    public static final String API_NAME = "以太坊提现记录控制器";
    public static final String METHOD_API_PARAM = "条件参数";
    public static final String METHOD_API_ID = "记录id";
    public static final String METHOD_API_PAGESIZE = "分页每页显示条数";
    public static final String METHOD_API_PAGENUM = "分页页码";

    public static class Check {
        public static final String METHOD_TITLE_NAME = "查询所有待审核的提现记录";
        public static final String METHOD_TITLE_NOTE = "查询所有待审核的提现记录";
    }

    public static class Recheck {
        public static final String METHOD_TITLE_NAME = "查询所有待复审的提现记录";
        public static final String METHOD_TITLE_NOTE = "查询所有待复审的提现记录";
    }

    public static class Pack {
        public static final String METHOD_TITLE_NAME = "查询所有打包中的提现记录";
        public static final String METHOD_TITLE_NOTE = "查询所有打包中的提现记录";
    }

    public static class Success {
        public static final String METHOD_TITLE_NAME = "查询提现成功的记录";
        public static final String METHOD_TITLE_NOTE = "查询提现成功的记录";
    }

    public static class Error {
        public static final String METHOD_TITLE_NAME = "查询提现失败的记录";
        public static final String METHOD_TITLE_NOTE = "查询提现失败的记录";
    }

    public static class Reject {
        public static final String METHOD_TITLE_NAME = "查询所有被驳回的提现记录";
        public static final String METHOD_TITLE_NOTE = "查询所有被驳回的提现记录";
    }

    public static class All {
        public static final String METHOD_TITLE_NAME = "查询所有提现的记录";
        public static final String METHOD_TITLE_NOTE = "查询所有提现的记录";
    }

    public static class SelectById {
        public static final String METHOD_TITLE_NAME = "根据id查询提现的记录";
        public static final String METHOD_TITLE_NOTE = "根据id查询提现的记录";

    }

    public static class CheckPass {
        public static final String METHOD_TITLE_NAME = "通过初审";
        public static final String METHOD_TITLE_NOTE = "通过初审";
    }

    public static class RecheckPass {
        public static final String METHOD_TITLE_NAME = "通过复审";
        public static final String METHOD_TITLE_NOTE = "通过复审";
    }

    public static class GoReject {
        public static final String METHOD_TITLE_NAME = "驳回";
        public static final String METHOD_TITLE_NOTE = "驳回";
    }

    public static class PackError{
        public static final String METHOD_TITLE_NAME = "打包失败";
        public static final String METHOD_TITLE_NOTE = "打包失败";
    }

    public static class PackSuccess{
        public static final String METHOD_TITLE_NAME = "打包成功";
        public static final String METHOD_TITLE_NOTE = "打包成功";
    }
}
