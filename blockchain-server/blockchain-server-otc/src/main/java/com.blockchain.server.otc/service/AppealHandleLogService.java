package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.appealhandlelog.ListAppealHandleLogResultDTO;

import java.util.List;

public interface AppealHandleLogService {

    /***
     * 查询申诉操作日志列表
     * @param orderNumber
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListAppealHandleLogResultDTO> listAppealHandleLog(String orderNumber, String beginTime, String endTime);

    /***
     * 新增申诉操作备注
     * @param sysUserId
     * @param ipAddress
     * @param orderNumber
     * @param afterStatus
     * @param remark
     * @return
     */
    int insertAppealHandleLog(String sysUserId, String ipAddress,
                              String orderNumber, String afterStatus, String remark);
}
