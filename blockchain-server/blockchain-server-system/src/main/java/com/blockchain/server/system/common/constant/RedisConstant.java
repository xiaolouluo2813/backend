package com.blockchain.server.system.common.constant;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 缓存到redis的key的规则信息
 * @author huangxl
 * @create 2019-02-23 15:45
 */
public class RedisConstant {
    private static final String SMS_HASH_KEY = "system:user:sms:{0}:{1}";//短信缓存值，第一个是短信类型，第二个是手机号
    /**
     * 获取手机验证码的redis key
     *
     * @param mobilePhone 手机号
     * @param type        类型
     */
    public static String getSmsHashKey(String type, String mobilePhone) {
        return MessageFormat.format(SMS_HASH_KEY, type, mobilePhone);
    }
}
