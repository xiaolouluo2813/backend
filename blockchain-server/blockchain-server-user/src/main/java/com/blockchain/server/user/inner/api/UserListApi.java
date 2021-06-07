package com.blockchain.server.user.inner.api;

/**
 * @author Harvey
 * @date 2019/3/9 11:59
 * @user WIN10
 */
public class UserListApi {
    public static final String USER_LIST_API = "用户黑白名单控制器";

    public static class ListBlacklist {
        public static final String METHOD_API_NAME = "查询用户黑名单信息";
        public static final String METHOD_API_NOTE = "根据用户id和名单类型查询用户黑名单信息";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class ListWhitelist {
        public static final String METHOD_API_NAME = "查询用户白名单信息";
        public static final String METHOD_API_NOTE = "根据用户id和名单类型查询用户白名单信息";

        public static final String METHOD_API_USER_ID = "用户id";
    }

    public static class ExistBlacklist {
        public static final String METHOD_API_NAME = "判断用户是否存在该黑名单";
        public static final String METHOD_API_NOTE = "根据用户id和类型判断用户是否存在该黑名单";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_TYPE = "类型";
    }

    public static class ExistWhitelist {
        public static final String METHOD_API_NAME = "判断用户是否存在该白名单";
        public static final String METHOD_API_NOTE = "根据用户id和类型判断用户是否存在该白名单";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_TYPE = "类型";
    }

}
