package com.blockchain.server.user.controller.api;

/**
 * @author Harvey
 * @date 2019/3/4 18:38
 * @user WIN10
 */
public class UserListApi {
    public static final String USER_LIST_API = "用户黑白名单控制器";

    public static final String METHOD_API_PAGE_NUM = "当前页数";
    public static final String METHOD_API_PAGE_SIZE = "当前页面内容";

    public static class ListBlacklist {
        public static final String METHOD_API_NAME = "查询黑名单列表";
        public static final String METHOD_API_NOTE = "根据黑名单类型查询黑名单列表";

        public static final String METHOD_API_TYPE = "类型";
    }

    public static class AddBlacklist {
        public static final String METHOD_API_NAME = "添加黑名单";
        public static final String METHOD_API_NOTE = "添加黑名单";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_TYPE = "类型";
    }

    public static class DelBlacklist {
        public static final String METHOD_API_NAME = "删除黑名单";
        public static final String METHOD_API_NOTE = "根据id删除黑名单";

        public static final String METHOD_API_ID = "数据id";
    }

    public static class ListWhitelist {
        public static final String METHOD_API_NAME = "查询白名单列表";
        public static final String METHOD_API_NOTE = "根据白名单类型查询黑名单列表";

        public static final String METHOD_API_TYPE = "类型";
    }

    public static class AddWhitelist {
        public static final String METHOD_API_NAME = "添加白名单";
        public static final String METHOD_API_NOTE = "添加白名单";

        public static final String METHOD_API_USER_ID = "用户id";
        public static final String METHOD_API_TYPE = "类型";
    }

    public static class DelWhitelist {
        public static final String METHOD_API_NAME = "删除白名单";
        public static final String METHOD_API_NOTE = "根据id删除白名单";

        public static final String METHOD_API_ID = "数据id";
    }

    public static class ListUserListByUserId {
        public static final String METHOD_API_NAME = "查询所有黑白名单";
        public static final String METHOD_API_NOTE = "根据用户id查询所有黑白名单";

        public static final String METHOD_API_USER_ID = "用户id";
    }

}
