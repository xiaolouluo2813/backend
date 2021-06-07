package com.blockchain.server.user.inner.api;

/**
 * @author Harvey
 * @date 2019/3/9 11:59
 * @user WIN10
 */
public class UserApi {
    public static final String USER_API = "用户管理控制器";

    public static class SelectUserInfo {
        public static final String METHOD_API_NAME = "查询用户信息";
        public static final String METHOD_API_NOTE = "查询用户信息";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class SelectUserInfoByMobilePhone {
        public static final String METHOD_API_NAME = "查询用户信息";
        public static final String METHOD_API_NOTE = "查询用户信息";

        public static final String METHOD_API_MOBILE_PHONE = "用户手机";
    }

    public static class ListUserInfo {
        public static final String METHOD_API_NAME = "根据id查询多个用户信息";
        public static final String METHOD_API_NOTE = "根据id查询多个用户信息";

        public static final String METHOD_API_USER_IDS = "多个用户id";
    }

    public static class SelectUserInfoByUserName {
        public static final String METHOD_API_NAME = "根据账户查询用户信息";
        public static final String METHOD_API_NOTE = "根据账户查询用户信息";

        public static final String METHOD_API_USERNAME = "账户";
    }

}
