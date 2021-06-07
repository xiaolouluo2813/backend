package com.blockchain.server.system.common.constant;

import java.text.MessageFormat;

/**
 * @author: Liusd
 * @create: 2019-03-27 16:59
 **/
public class LoginConstant {

    public static final String LOGIN_KEY = "login:key:{0}";


    public static String getLoginKey(String username) {
        return MessageFormat.format(LOGIN_KEY,username);
    }
}
