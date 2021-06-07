package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.appealhandlelog.ListAppealHandleLogResultDTO;
import com.blockchain.server.otc.entity.AppealHandleLog;
import com.blockchain.server.otc.mapper.AppealHandleLogMapper;
import com.blockchain.server.otc.service.AppealHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AppealHandleLogServiceImpl implements AppealHandleLogService {

    @Autowired
    private AppealHandleLogMapper appealHandleLogMapper;

    @Override
    public List<ListAppealHandleLogResultDTO> listAppealHandleLog(String orderNumber, String beginTime, String endTime) {
        return appealHandleLogMapper.listAppealHandleLog(orderNumber, beginTime, endTime);
    }

    @Override
    @Transactional
    public int insertAppealHandleLog(String sysUserId, String ipAddress, String orderNumber, String afterStatus, String remark) {
        AppealHandleLog appealHandleLog = new AppealHandleLog();
        appealHandleLog.setId(UUID.randomUUID().toString());
        appealHandleLog.setSysUserId(sysUserId);
        appealHandleLog.setIpAddress(ipAddress);
        appealHandleLog.setOrderNumber(orderNumber);
        appealHandleLog.setAfterStatus(afterStatus);
        appealHandleLog.setRemark(remark);
        appealHandleLog.setCreateTime(new Date());
        return appealHandleLogMapper.insertSelective(appealHandleLog);
    }
}
