package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.appeal.ListAppealResultDTO;
import com.blockchain.server.otc.entity.Appeal;

import java.util.List;

public interface AppealService {

    /***
     * 查询申诉列表
     * @param orderNumber
     * @param status
     * @return
     */
    List<ListAppealResultDTO> listAppeal(String orderNumber, String status, String beginTime, String endTime);

    /***
     * 处理申诉-使订单变为完成状态
     * 买家胜诉、卖家败诉
     *
     * @param appealId
     * @param sysUserId
     * @param ipAddress
     * @param remark
     */
    void handleFinishOrder(String appealId, String sysUserId, String ipAddress, String remark);

    /***
     * 处理申诉-使订单变为失效状态
     * 买家败诉、卖家胜诉
     *
     * @param appealId
     * @param sysUserId
     * @param ipAddress
     * @param remark
     */
    void handleCancelOrder(String appealId, String sysUserId, String ipAddress, String remark);
}
