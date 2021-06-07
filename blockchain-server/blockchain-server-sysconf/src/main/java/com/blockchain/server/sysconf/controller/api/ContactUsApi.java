package com.blockchain.server.sysconf.controller.api;

/**
 * @author: Liusd
 * @create: 2019-04-01 14:24
 **/
public class ContactUsApi {

    public static final String CONTROLLER_API = "联系我们管理控制器";

    public static class List {
        public static final String METHOD_TITLE_NAME = "列表接口";
        public static final String METHOD_TITLE_NOTE = "列表接口";
        public static final String METHOD_API_CONTACTUSDTO = "条件查询";
        public static final String METHOD_API_PAGENUM="查询页码";
        public static final String METHOD_API_PAGESIZE="每页记录数";
    }

    public static class Add {
        public static final String METHOD_TITLE_NAME = "新增接口";
        public static final String METHOD_TITLE_NOTE = "新增接口";
        public static final String METHOD_API_CONTACTUS="数据";
    }

    public static class Edit {
        public static final String METHOD_TITLE_NAME = "编辑接口";
        public static final String METHOD_TITLE_NOTE = "编辑接口";
        public static final String METHOD_API_CONTACTUS="数据";
    }

    public static class Remove {
        public static final String METHOD_TITLE_NAME = "删除接口";
        public static final String METHOD_TITLE_NOTE = "删除接口";
        public static final String METHOD_API_ID="id";
    }

}
