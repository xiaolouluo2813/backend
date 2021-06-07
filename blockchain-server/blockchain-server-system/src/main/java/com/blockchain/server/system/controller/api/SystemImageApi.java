package com.blockchain.server.system.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-25 13:51
 **/
public class SystemImageApi {

    public static final String CONTROLLER_API = "轮播图管理控制器";

    public static class InsertSystemImage {
        public static final String METHOD_TITLE_NAME = "新增系统用户接口";
        public static final String METHOD_TITLE_NOTE = "系统用户新增接口";
        public static final String METHOD_API_DETAILS = "公告内容";
        public static final String METHOD_API_JUMPURL = "跳转地址";
        public static final String METHOD_API_STATUS = "显示状态： 显示(Y)，隐藏(N)";
        public static final String METHOD_API_RANK = "序号";
    }

    public static class UpdateSystemImage {
        public static final String METHOD_TITLE_NAME = "修改系统用户基本信息接口";
        public static final String METHOD_TITLE_NOTE = "系统用户修改信息接口";
        public static final String METHOD_API_DETAILS = "公告内容";
        public static final String METHOD_API_JUMPURL = "跳转地址";
        public static final String METHOD_API_RANK = "序号";
        public static final String METHOD_API_ID = "用户id";
    }

    public static class UpdateSystemImageStatus {
        public static final String METHOD_TITLE_NAME = "修改系统用户状态接口";
        public static final String METHOD_TITLE_NOTE = "修改系统用户状态接口";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_ID = "用户id";
    }

    public static class SystemImageList {

        public static final String METHOD_TITLE_NAME = "系统用户列表接口";
        public static final String METHOD_TITLE_NOTE = "系统用户列表接口，可根据状态查询";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_NAME = "名称";
        public static final String METHOD_API_PHONE = "手机号";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class DeleteImage {
        public static final String METHOD_TITLE_NAME = "删除用户接口";
        public static final String METHOD_TITLE_NOTE = "删除用户";
        public static final String METHOD_API_ID = "用户id";
    }
}
