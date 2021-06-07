package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.marketapplyhandlelog.ListMarketApplyHandleLogResultDTO;

import java.util.List;

public interface MarketApplyHandleLogService {

    /***
     * 新增申请操作日志
     * @param applyId
     * @param sysUserId
     * @param ipAddress
     * @param beforeStatus
     * @param afterStatus
     * @return
     */
    int insertApplyHandleLog(String applyId, String sysUserId, String ipAddress,
                             String beforeStatus, String afterStatus);

    /***
     * 查询申请操作日志列表
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketApplyHandleLogResultDTO> list(String beginTime, String endTime);
}
