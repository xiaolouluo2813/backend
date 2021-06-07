package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.marketapplyhandlelog.ListMarketApplyHandleLogResultDTO;
import com.blockchain.server.otc.entity.MarketApplyHandleLog;
import com.blockchain.server.otc.mapper.MarketApplyHandleLogMapper;
import com.blockchain.server.otc.service.MarketApplyHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MarketApplyHandleLogServiceImpl implements MarketApplyHandleLogService {

    @Autowired
    private MarketApplyHandleLogMapper marketApplyHandleLogMapper;

    @Override
    @Transactional
    public int insertApplyHandleLog(String applyId, String sysUserId, String ipAddress,
                                    String beforeStatus, String afterStatus) {
        MarketApplyHandleLog handleLog = new MarketApplyHandleLog();
        String handleLogId = UUID.randomUUID().toString();
        handleLog.setId(handleLogId);
        handleLog.setMarketApplyId(applyId);
        handleLog.setSysUserId(sysUserId);
        handleLog.setIpAddress(ipAddress);
        handleLog.setBeforeStatus(beforeStatus);
        handleLog.setAfterStatus(afterStatus);
        handleLog.setCreateTime(new Date());
        return marketApplyHandleLogMapper.insertSelective(handleLog);
    }

    @Override
    public List<ListMarketApplyHandleLogResultDTO> list(String beginTime, String endTime) {
        return marketApplyHandleLogMapper.list(beginTime, endTime);
    }
}
