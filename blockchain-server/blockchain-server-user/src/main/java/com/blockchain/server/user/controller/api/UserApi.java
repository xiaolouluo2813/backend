package com.blockchain.server.user.controller.api;

/**
 * @author Harvey
 * @date 2019/3/4 18:38
 * @user WIN10
 */
public class UserApi {
    public static final String USER_API = "用户控制器";

    public static final String METHOD_API_PAGE_NUM = "当前页数";
    public static final String METHOD_API_PAGE_SIZE = "当前页面内容";

    public static class ListSearchUser {
        public static final String METHOD_API_NAME = "查询用户列表";
        public static final String METHOD_API_NOTE = "根据要求查询用户列表";

        public static final String METHOD_API_MOBILE_PHONE = "手机号码";
        public static final String METHOD_API_EMAIL = "电子邮箱";
        public static final String METHOD_API_LOW_AUTH = "初级认证";
        public static final String METHOD_API_HIGH_AUTH = "高级认证";
        public static final String METHOD_API_START_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
    }

    public static class RealNameAudit {
        public static final String METHOD_API_NAME = "实名审核";
        public static final String METHOD_API_NOTE = "实名审核";

        public static final String METHOD_API_MOBILE_PHONE = "手机号码";
        public static final String METHOD_API_REAL_NAME = "姓名";
        public static final String METHOD_API_LOW_AUTH = "初级认证";
        public static final String METHOD_API_HIGH_AUTH = "高级认证";
        public static final String METHOD_API_START_TIME = "开始时间";
        public static final String METHOD_API_END_TIME = "结束时间";
        public static final String METHOD_API_SORT = "排序";
    }

    public static class UserAssets {
        public static final String METHOD_API_NAME = "用户资产";
        public static final String METHOD_API_NOTE = "用户资产";

        public static final String METHOD_API_MOBILE_PHONE = "手机号码";
        public static final String METHOD_API_REAL_NAME = "姓名";
        public static final String METHOD_API_EMAIL = "邮箱";
        public static final String METHOD_API_DATE = "日期";
    }

    public static class SelectUserInfo {
        public static final String METHOD_API_NAME = "查询用户信息";
        public static final String METHOD_API_NOTE = "根据用户id查询用户信息";

        public static final String METHOD_API_USER_id = "用户id";
    }

    public static class ListSearchUserAsset {
        public static final String METHOD_API_NAME = "查询用户资产信息";
        public static final String METHOD_API_NOTE = "根据条件查询用户资产信息";

        public static final String METHOD_API_REAL_NAME = "姓名";
        public static final String METHOD_API_MOBILE_PHONE = "手机号";
        public static final String METHOD_API_EMAIL = "邮箱";
    }

    public static class UpdateMobilePhone {
        public static final String METHOD_API_NAME = "修改用户手机";
        public static final String METHOD_API_NOTE = "修改用户手机";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_MUBILE_PHONE = "手机号码";
    }

    public static class UpdateIdentityCode {
        public static final String METHOD_API_NAME = "修改用户手机";
        public static final String METHOD_API_NOTE = "修改用户手机";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_IDENTITY_CODE = "身份证号码";
    }
}
