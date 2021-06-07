package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.adhandlelog.InsertAdHandleLogParamDTO;
import com.blockchain.server.otc.dto.adhandlelog.ListAdHandleLogResultDTO;

import java.util.List;

public interface AdHandleLogService {

    /***
     * 查询广告操作日志列表
     * @param adNumber
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListAdHandleLogResultDTO> listAdHandleLog(String adNumber, String beginTime, String endTime);

    /***
     * 新增广告处理日志
     * @param paramDTO
     * @return
     */
    int insertAdHandleLog(InsertAdHandleLogParamDTO paramDTO);
}
