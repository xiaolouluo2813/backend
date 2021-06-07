package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.adhandlelog.InsertAdHandleLogParamDTO;
import com.blockchain.server.otc.dto.adhandlelog.ListAdHandleLogResultDTO;
import com.blockchain.server.otc.entity.AdHandleLog;
import com.blockchain.server.otc.mapper.AdHandleLogMapper;
import com.blockchain.server.otc.service.AdHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AdHandleLogServiceImpl implements AdHandleLogService {

    @Autowired
    private AdHandleLogMapper adHandleLogMapper;

    @Override
    public List<ListAdHandleLogResultDTO> listAdHandleLog(String adNumber, String beginTime, String endTime) {
        return adHandleLogMapper.listAdHandleLog(adNumber, beginTime, endTime);
    }

    @Override
    @Transactional
    public int insertAdHandleLog(InsertAdHandleLogParamDTO paramDTO) {
        AdHandleLog adHandleLog = new AdHandleLog();
        adHandleLog.setId(UUID.randomUUID().toString());
        adHandleLog.setSysUserId(paramDTO.getSysUserId());
        adHandleLog.setIpAddress(paramDTO.getIpAddress());
        adHandleLog.setAdNumber(paramDTO.getAdNumber());
        adHandleLog.setBeforeStatus(paramDTO.getBeforeStatus());
        adHandleLog.setAfterStatus(paramDTO.getAfterStatus());
        adHandleLog.setCreateTime(new Date());
        return adHandleLogMapper.insertSelective(adHandleLog);
    }
}
