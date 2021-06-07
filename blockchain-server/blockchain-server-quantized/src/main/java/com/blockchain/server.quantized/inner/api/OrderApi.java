package com.blockchain.server.quantized.inner.api;

/**
 * @author: Liusd
 * @create: 2019-04-18 20:11
 **/
public class OrderApi {

    public static final String ORDER_API = "订单控制器";
    /** 
    * @Description: 撤单 
    * @Param:  
    * @return:  
    * @Author: Liu.sd 
    * @Date: 2019/4/19 
    */ 
    public static class Cancel {
        public static final String METHOD_TITLE_NAME = "撤单接口";
        public static final String METHOD_TITLE_NOTE = "撤单接口";
        public static final String SYMBOL="交易对";
        public static final String ORDERID="订单号";
    }
    
    /** 
    * @Description: 订单详情
    * @Param:  
    * @return:  
    * @Author: Liu.sd 
    * @Date: 2019/4/19 
    */ 
    public static class Details {
        public static final String METHOD_TITLE_NAME = "订单详情接口";
        public static final String METHOD_TITLE_NOTE = "订单详情接口";
        public static final String SYMBOL="交易对";
        public static final String ORDERID="订单号";
    }
    public static class Balance {
        public static final String METHOD_TITLE_NAME = "账户余额接口";
        public static final String METHOD_TITLE_NOTE = "账户余额接口";
    }
}
