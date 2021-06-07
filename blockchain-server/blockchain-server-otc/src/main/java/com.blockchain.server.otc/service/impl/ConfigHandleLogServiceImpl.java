package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.confighandlelog.InsertConfigHandleLogParamDTO;
import com.blockchain.server.otc.dto.confighandlelog.ListConfigHandleLogResultDTO;
import com.blockchain.server.otc.entity.ConfigHandleLog;
import com.blockchain.server.otc.mapper.ConfigHandleLogMapper;
import com.blockchain.server.otc.service.ConfigHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ConfigHandleLogServiceImpl implements ConfigHandleLogService {

    @Autowired
    private ConfigHandleLogMapper configHandleLogMapper;

    @Override
    public List<ListConfigHandleLogResultDTO> listConfigHandleLog(String dataKey, String beginTime, String endTime) {
        return configHandleLogMapper.listConfigHandleLog(dataKey, beginTime, endTime);
    }

    @Override
    @Transactional
    public int insertConfigHandleLog(InsertConfigHandleLogParamDTO paramDTO) {
        ConfigHandleLog configHandleLog = new ConfigHandleLog();
        configHandleLog.setId(UUID.randomUUID().toString());
        configHandleLog.setDataKey(paramDTO.getDataKey());
        configHandleLog.setSysUserId(paramDTO.getSysUserId());
        configHandleLog.setIpAddress(paramDTO.getIpAddress());
        configHandleLog.setBeforeValue(paramDTO.getBeforeValue());
        configHandleLog.setAfterValue(paramDTO.getAfterValue());
        configHandleLog.setBeforeStatus(paramDTO.getBeforeStatus());
        configHandleLog.setAfterStatus(paramDTO.getAfterStatus());
        configHandleLog.setCreateTime(new Date());
        return configHandleLogMapper.insertSelective(configHandleLog);
    }
}
