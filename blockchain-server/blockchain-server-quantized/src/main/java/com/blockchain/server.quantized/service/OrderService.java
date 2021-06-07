package com.blockchain.server.quantized.service;

import com.huobi.client.model.BatchCancelResult;
import com.huobi.client.model.Order;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-18 19:34
 **/
public interface OrderService {

    /** 
    * @Description: 撤单 
    * @Param: [symbol, orderId] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/4/20 
    */
    String cancellations(String symbol, Long orderId);

    /**
    * @Description: 定时任务/初始化(更新数据库数据)
    * @Param: []
    * @return: void
    * @Author: Liu.sd
    * @Date: 2019/4/20
    */
    void initOrder();

    List<Order> getOpenOrders(String symbol);

    BatchCancelResult cancelAll(String symbol);

    String cancel(String cctId);
}
