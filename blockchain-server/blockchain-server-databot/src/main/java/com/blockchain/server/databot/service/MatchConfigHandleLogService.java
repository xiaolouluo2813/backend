package com.blockchain.server.databot.service;

import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigHandleLogResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;
import com.blockchain.server.databot.entity.MatchConfig;
import com.blockchain.server.databot.entity.MatchConfigHandleLog;

import java.util.List;

public interface MatchConfigHandleLogService {

    /***
     * 根据操作类型（新增、更新、删除）新增操作日志
     * @param config
     * @param updateConfig
     * @param sysUserId
     * @param ipAddress
     * @param handleType
     * @return
     */
    int insertHandleLog(MatchConfig config, UpdateMatchConfigParamDTO updateConfig, String sysUserId,
                        String ipAddress, String handleType);

    /***
     * 查询操作日志列表
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMatchConfigHandleLogResultDTO> listHandleLog(String handleType, String beginTime, String endTime);
}
