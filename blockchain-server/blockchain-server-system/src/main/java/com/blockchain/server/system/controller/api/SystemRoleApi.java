package com.blockchain.server.system.controller.api;

/**
 * @author huangxl
 * @create 2018-11-16 14:51
 */
public class SystemRoleApi {
    public static final String CONTROLLER_API = "系统角色控制器";

    public static class CodeCheck {

        public static final String METHOD_TITLE_NAME = "角色标识唯一性校验接口";
        public static final String METHOD_TITLE_NOTE = "角色标识唯一性";
        public static final String METHOD_API_CODE = "角色标识";
    }

    public static class InsertSystemRole {
        public static final String METHOD_TITLE_NAME = "新增系统角色接口";
        public static final String METHOD_TITLE_NOTE = "系统角色新增接口";
        public static final String METHOD_API_NAME = "角色名称";
        public static final String METHOD_API_CODE = "角色标识";
        public static final String METHOD_API_RANKING = "角色序号";
    }

    public static class UpdateSystemRole {
        public static final String METHOD_TITLE_NAME = "修改系统角色基本信息接口";
        public static final String METHOD_TITLE_NOTE = "系统角色修改信息接口";
        public static final String METHOD_API_NAME = "角色名称";
        public static final String METHOD_API_CODE = "角色标识";
        public static final String METHOD_API_RANKING = "角色序号";
        public static final String METHOD_API_ID = "角色id";
    }

    public static class UpdateSystemRoleStatus {
        public static final String METHOD_TITLE_NAME = "修改角色状态接口";
        public static final String METHOD_TITLE_NOTE = "修改角色状态接口";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_ID = "角色id";
    }

    public static class SystemRoleList {
        public static final String METHOD_TITLE_NAME = "系统角色列表接口";
        public static final String METHOD_TITLE_NOTE = "系统角色列表接口，可根据状态查询";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
        public static final String METHOD_API_NAME = "角色名称";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class SetRoleMenus {
        public static final String METHOD_TITLE_NAME = "设置角色菜单接口";
        public static final String METHOD_TITLE_NOTE = "设置角色菜单接口，ids用"+","+"拼接";
        public static final String METHOD_API_MENUIDS = "菜单ids";
        public static final String METHOD_API_ID = "角色id";
    }

    public static class UserRoleList {
        public static final String METHOD_TITLE_NAME = "用户角色接口";
        public static final String METHOD_TITLE_NOTE = "用户角色列表";
        public static final String METHOD_API_USERID = "用户id";
        public static final String METHOD_API_STATUS = "状态  可用(Y)、禁用(N)";
    }
    public static class DeleteRole {
        public static final String METHOD_TITLE_NAME = "删除角色接口";
        public static final String METHOD_TITLE_NOTE = "删除角色";
        public static final String METHOD_API_ID = "角色id";
    }
    public static class RoleMenuList {
        public static final String METHOD_TITLE_NAME = "角色菜单接口";
        public static final String METHOD_TITLE_NOTE = "角色菜单角色";
        public static final String METHOD_API_ID = "角色id";
    }
}
