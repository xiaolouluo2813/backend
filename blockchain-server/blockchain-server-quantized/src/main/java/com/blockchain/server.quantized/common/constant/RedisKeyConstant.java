package com.blockchain.server.quantized.common.constant;

import java.text.MessageFormat;

/**
 * @author: Liusd
 * @create: 2019-04-26 12:07
 **/
public class RedisKeyConstant {

    public static final String TRADING_ON_CANCEL = "quantized:trdingon:cancel:{0}-{1}";

    public static final String STATUS = "Y";


    public static final Long TIME_OUT = 2L;



    public static String getTradingOnCancel(String coinName,String unitName) {
        return MessageFormat.format(TRADING_ON_CANCEL,coinName,unitName);
    }
}
