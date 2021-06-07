package com.blockchain.server.cct.service;

import com.blockchain.server.cct.entity.PublishOrderLog;

import java.util.List;

public interface PublishOrderLogService {

    /***
     * 插入订单操作记录
     * @param orderLog
     * @return
     */
    int insertOrderLog(PublishOrderLog orderLog);

    /***
     * 查询订单操作记录
     * @return
     */
    List<PublishOrderLog> listOrderLog();
}
