package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.userhandlelog.ListUserHandleLogResultDTO;

import java.util.List;

public interface UserHandleLogService {

    /***
     * 列表查询用户操作记录
     * @param userName
     * @param handleNumber
     * @param handleNumberType
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListUserHandleLogResultDTO> listUserHandleLog(String userName, String handleNumber, String handleNumberType,
                                                       String handleType, String beginTime, String endTime);
}
