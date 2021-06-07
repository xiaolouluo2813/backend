package com.blockchain.server.cct.service;

import com.blockchain.server.cct.dto.publishOrder.ListOrderParamDTO;
import com.blockchain.server.cct.dto.publishOrder.ListOrderResultDTO;
import com.blockchain.server.cct.entity.PublishOrder;

import java.util.List;

public interface PublishOrderService {

    /***
     * 查询委托订单列表
     * @param param
     * @return
     */
    List<ListOrderResultDTO> listOrder(ListOrderParamDTO param);

    /***
     * 使用排他锁查询订单
     * @param orderId
     * @return
     */
    PublishOrder selectOrderForUpdate(String orderId);

    /***
     * 撤销订单
     * @param sysUserId 管理员ID
     * @param ipAddr 管理员IP地址
     * @param orderId 订单id
     * @return
     */
    int cancel(String sysUserId, String ipAddr, String orderId);
}
