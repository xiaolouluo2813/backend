package com.blockchain.server.system.controller.api;

/**
 * @author: Liusd
 * @create: 2019-03-05 18:45
 **/
public class LoginApi {

    public static final String CONTROLLER_API = "登录控制器";

    public static class Login {
        public static final String METHOD_TITLE_NAME = "用户登录接口";
        public static final String METHOD_TITLE_NOTE = "用户登录";
        public static final String METHOD_API_USERNAME = "用户名";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_PHONE = "手机号";
        public static final String METHOD_API_CODE = "验证码";
    }
    public static class Exit {
        public static final String METHOD_TITLE_NAME = "用户退出登录接口";
        public static final String METHOD_TITLE_NOTE = "用户退出登录";
    }

    public static class GetPublicKey {
        public static final String METHOD_API_NAME = "获取密码加密的公钥";
        public static final String METHOD_API_NOTE = "获取密码加密的公钥";
        public static final String METHOD_API_USERNAME = "用户名";
    }

    public static class SendLoginSmsCode{
        public static final String METHOD_NAME = "发送登录验证码";
        public static final String METHOD_NOTE = "发送登录验证码";
        public static final String METHOD_API_USERNAME = "用户名";
        public static final String METHOD_API_PASSWORD = "密码";
        public static final String METHOD_API_PHONE="手机号";
    }
}
