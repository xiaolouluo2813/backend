package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.confighandlelog.InsertConfigHandleLogParamDTO;
import com.blockchain.server.otc.dto.confighandlelog.ListConfigHandleLogResultDTO;

import java.util.List;

public interface ConfigHandleLogService {
    /***
     * 查询配置操作日志列表
     * @param dataKey
     * @return
     */
    List<ListConfigHandleLogResultDTO> listConfigHandleLog(String dataKey, String beginTime, String endTime);

    /***
     * 新增配置操作日志
     * @param paramDTO
     * @return
     */
    int insertConfigHandleLog(InsertConfigHandleLogParamDTO paramDTO);
}
