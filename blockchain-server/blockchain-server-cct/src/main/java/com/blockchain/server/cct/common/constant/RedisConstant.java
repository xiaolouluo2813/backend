package com.blockchain.server.cct.common.constant;

import java.text.MessageFormat;

public class RedisConstant {

    private static final String REDIS_LOCK_ORDER_KEY = "cct:distributed:lock:order:{0}:{1}-{2}"; //订单数据锁key
    private static final String REDIS_ORDER_KEY = "cct:list:order:{0}:{1}-{2}"; //订单数据key

    /***
     * 获取订单数据锁key
     * @param orderType
     * @param coinName
     * @param unitName
     * @return
     */
    public static String getLockOrderKey(String orderType, String coinName, String unitName) {
        return MessageFormat.format(REDIS_LOCK_ORDER_KEY, orderType, coinName, unitName);
    }

    /***
     * 获取订单数据key
     * @param orderType
     * @param coinName
     * @param unitName
     * @return
     */
    public static String getOrderKey(String orderType, String coinName, String unitName) {
        return MessageFormat.format(REDIS_ORDER_KEY, orderType, coinName, unitName);
    }
}
