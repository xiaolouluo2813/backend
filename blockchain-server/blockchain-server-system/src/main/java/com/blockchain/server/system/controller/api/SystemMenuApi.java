package com.blockchain.server.system.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-05 17:04
 **/
public class SystemMenuApi {
    public static final String CONTROLLER_API = "系统菜单控制器";

    public static class InsertSystemMenu {
        public static final String METHOD_TITLE_NAME = "新增系统菜单接口";
        public static final String METHOD_TITLE_NOTE = "系统菜单新增接口";
        public static final String METHOD_API_SYSTEMMENUADDDTO = "新增系统菜单基本信息";
    }

    public static class CodeCheck {
        public static final String METHOD_TITLE_NAME = "菜单标识唯一性校验接口";
        public static final String METHOD_TITLE_NOTE = "菜单标识唯一性";
        public static final String METHOD_API_CODE = "菜单标识";
    }

    public static class SysteMenuList {
        public static final String METHOD_TITLE_NAME = "菜单列表接口";
        public static final String METHOD_TITLE_NOTE = "全部菜单";
    }

    public static class UserMenuList {
        public static final String METHOD_TITLE_NAME = "用户菜单列表接口";
        public static final String METHOD_TITLE_NOTE = "用户拥有菜单";
        public static final String METHOD_API_USERID = "用户id";
    }
}
