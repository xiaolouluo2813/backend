package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.orderhandlelog.ListOrderHandleLogResultDTO;

import java.util.List;

public interface OrderHandleLogService {

    /***
     * 查询订单操作日志列表
     * @param orderNumber
     * @return
     */
    List<ListOrderHandleLogResultDTO> listOrderHandleLog(String orderNumber);

    /***
     * 新增订单操作日志列表
     * @param sysUserId
     * @param ipAddress
     * @param orderNumber
     * @param beforeStatus
     * @param afterStatus
     * @return
     */
    int insertOrderHandleLog(String sysUserId, String ipAddress,
                             String orderNumber, String beforeStatus,
                             String afterStatus);
}
