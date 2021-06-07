package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.marketuserhandlelog.ListMarketUserHandleLogResultDTO;

import java.util.List;

public interface MarketUserHandleLogService {

    /***
     * 新增市商用户操作日志
     * @param ipAddress
     * @param sysUserId
     * @param userId
     * @param beforeStatus
     * @param afterStatus
     * @return
     */
    String insertUserHandleLog(String ipAddress, String sysUserId, String userId,
                               String beforeStatus, String afterStatus);

    /***
     * 查询市商用户操作日志
     * @param userName
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketUserHandleLogResultDTO> list(String userName, String beginTime, String endTime);
}
