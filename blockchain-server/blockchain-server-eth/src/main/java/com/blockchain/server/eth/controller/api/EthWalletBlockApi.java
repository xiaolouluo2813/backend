package com.blockchain.server.eth.controller.api;

public class EthWalletBlockApi {
    public static final String API_NAME = "以太坊钱包控制器";
    public static final String METHOD_API_PARAM = "条件参数";
    public static final String METHOD_API_PAGESIZE = "分页每页显示条数";
    public static final String METHOD_API_PAGENUM = "分页页码";
    public static final String METHOD_API_ADDR = "钱包地址";
    public static final String METHOD_API_COINNAME = "币种名称";
    public static final String METHOD_API_FREEBLANCE = "可用余额";
    public static final String METHOD_API_FREEZEBLANCE = "冻结余额";


    public static class All {
        public static final String METHOD_TITLE_NAME = "查询用户链上钱包详情信息";
        public static final String METHOD_TITLE_NOTE = "查询用户链上钱包详情信息";
    }

    public static class AddGas {
        public static final String METHOD_TITLE_NAME = "打燃料费用";
        public static final String METHOD_TITLE_NOTE = "打燃料费用";
    }

    public static class GoTx {
        public static final String METHOD_TITLE_NAME = "归集";
        public static final String METHOD_TITLE_NOTE = "归集";
    }
    public static class GetGas{
        public static final String METHOD_TITLE_NAME = "估算手续费";
        public static final String METHOD_TITLE_NOTE = "估算手续费";
    }

    public static class GetGasWallet{
        public static final String METHOD_TITLE_NAME = "获取油费钱包";
        public static final String METHOD_TITLE_NOTE = "获取油费钱包";
    }

}
