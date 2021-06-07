package com.blockchain.server.otc.controller.api;

public class AdApi {
    public final static String AD_API = "广告控制器";
    public final static String METHOD_API_PAGE_NUM = "页码";
    public final static String METHOD_API_PAGE_SIZE = "每页显示条数";

    public static class listAd {
        public static final String METHOD_TITLE_NAME = "查询广告列表";
        public static final String METHOD_TITLE_NOTE = "查询广告列表";
        public static final String METHOD_API_AD_NUMBER = "广告流水号";
        public static final String METHOD_API_USER_NAME = "账户/手机号";
        public static final String METHOD_API_COIN_NAME = "币种/基本货币";
        public static final String METHOD_API_UNIT_NAME = "单位/二级货币";
        public static final String METHOD_API_AD_TYPE = "广告类型";
        public static final String METHOD_API_AD_STATUS = "广告状态";
        public static final String METHOD_API_BEGIN_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }

    public static class cancelAd {
        public static final String METHOD_TITLE_NAME = "撤销广告";
        public static final String METHOD_TITLE_NOTE = "撤销广告";
        public static final String METHOD_API_AD_ID = "广告ID";
    }
}
