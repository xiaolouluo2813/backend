package com.blockchain.server.system.common.enums;


import com.blockchain.server.system.common.constant.SmsCountConstant;

import java.util.HashMap;
import java.util.Map;

public enum SmsCountEnum {

    SMS_COUNT_REGISTER(SmsCountConstant.SMS_TYPE_REGISTER, 10, "register", "SMS_165691638"),     //单天最多收10条注册短信
    SMS_COUNT_SET_PASSWORD(SmsCountConstant.SMS_TYPE_SET_PASSWORD, 10, "setPassword", "SMS_165691638"),      //设置密码验证码
    SMS_COUNT_UPDATE_ACCOUNT(SmsCountConstant.SMS_TYPE_UPDATE_ACCOUNT, 5, "updateAccount", "SMS_165691638"),      //更新账号信息，修改手机号（用户名）
    SMS_COUNT_LOGIN(SmsCountConstant.SMS_TYPE_LOGIN, 10, "login", "SMS_165691638"),      //登陆，短信验证码
    SMS_COUNT_FORGET_PASSWORD(SmsCountConstant.SMS_TYPE_FORGET_PASSWORD, 10, "forgetPassword", "SMS_165691638"),      //找回密码验证码
    SMS_COUNT_UPDATE_WALLET_PASSWORD(SmsCountConstant.SMS_TYPE_CHANGE_WALLET_PASSWORD, 10, "changeWalletPassword", "SMS_165691638"),//重置、修改资金密码
    ;

    private String type;//类型,用于保存到数据库
    private int maxCount;//每天最大短信
    private String key;//存到缓存的key标识
    private String templeteId;//模板id

    SmsCountEnum(String type, int maxCount, String key, String templeteId) {
        this.type = type;
        this.maxCount = maxCount;
        this.key = key;
        this.templeteId = templeteId;
    }

    private static final Map<String, SmsCountEnum> map = new HashMap<>();

    static {
        for (SmsCountEnum smsCountEnum : values()) {
            map.put(smsCountEnum.type, smsCountEnum);
        }
    }

    public static SmsCountEnum parse(int type) {
        return map.get(type);
    }

    public String getType() {
        return type;
    }

    public int getMaxCount() {
        return this.maxCount;
    }

    public String getKey() {
        return key;
    }

    public String getTempleteId() {
        return templeteId;
    }
}
