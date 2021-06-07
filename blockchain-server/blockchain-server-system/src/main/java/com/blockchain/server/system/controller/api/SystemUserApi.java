package com.blockchain.server.system.controller.api;

/**
 * @author huangxl
 * @create 2018-11-16 14:51
 */
public class SystemUserApi {
    public static final String CONTROLLER_API = "系统用户控制器";

    public static class InsertSystemUser {
        public static final String METHOD_TITLE_NAME = "新增系统用户接口";
        public static final String METHOD_TITLE_NOTE = "系统用户新增接口";
        public static final String METHOD_API_SYSTEMUSERADDDTO = "新增系统用户基本信息";
    }

    public static class AccountCheck {

        public static final String METHOD_TITLE_NAME = "系统用户账户名唯一性校验接口";
        public static final String METHOD_TITLE_NOTE = "新增修改校验账户名唯一性";
        public static final String METHOD_API_ACCOUNT = "账户名";
    }

    public static class UpdateSystemUser {
        public static final String METHOD_TITLE_NAME = "修改系统用户基本信息接口";
        public static final String METHOD_TITLE_NOTE = "系统用户修改信息接口";
        public static final String METHOD_API_SYSTEMUSERADDDTO = "用户基本信息";
        public static final String METHOD_API_ID = "用户id";
    }

    public static class UpdateSystemUserStatus {
        public static final String METHOD_TITLE_NAME = "修改系统用户状态接口";
        public static final String METHOD_TITLE_NOTE = "修改系统用户状态接口";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_ID = "用户id";
    }

    public static class SystemUserList {

        public static final String METHOD_TITLE_NAME = "系统用户列表接口";
        public static final String METHOD_TITLE_NOTE = "系统用户列表接口，可根据状态查询";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_NAME = "名称";
        public static final String METHOD_API_PHONE = "手机号";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class SetUserRoles {
        public static final String METHOD_TITLE_NAME = "设置系统用户角色接口";
        public static final String METHOD_TITLE_NOTE = "修改系统用户角色接口，ids用"+","+"拼接";
        public static final String METHOD_API_ROLEIDS = "角色ids";
        public static final String METHOD_API_ID = "用户id";
    }

    public static class UpdateUserPassword {
        public static final String METHOD_TITLE_NAME = "修改密码接口";
        public static final String METHOD_TITLE_NOTE = "修改密码";
        public static final String METHOD_API_ID = "用户id";
        public static final String METHOD_API_OLDPASSWORD = "原密码";
        public static final String METHOD_API_NEWPASSWORD = "新密码";
    }

    public static class DeleteUser {
        public static final String METHOD_TITLE_NAME = "删除用户接口";
        public static final String METHOD_TITLE_NOTE = "删除用户";
        public static final String METHOD_API_ID = "用户id";
    }
}
