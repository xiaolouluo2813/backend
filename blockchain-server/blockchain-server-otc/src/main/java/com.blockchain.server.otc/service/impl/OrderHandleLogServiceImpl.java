package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.dto.orderhandlelog.ListOrderHandleLogResultDTO;
import com.blockchain.server.otc.entity.OrderHandleLog;
import com.blockchain.server.otc.mapper.OrderHandleLogMapper;
import com.blockchain.server.otc.service.OrderHandleLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderHandleLogServiceImpl implements OrderHandleLogService {

    @Autowired
    private OrderHandleLogMapper orderHandleLogMapper;

    @Override
    public List<ListOrderHandleLogResultDTO> listOrderHandleLog(String orderNumber) {
        return orderHandleLogMapper.listOrderHandleLog(orderNumber);
    }

    @Override
    @Transactional
    public int insertOrderHandleLog(String sysUserId, String ipAddress, String orderNumber, String beforeStatus, String afterStatus) {
        OrderHandleLog orderHandleLog = new OrderHandleLog();
        orderHandleLog.setId(UUID.randomUUID().toString());
        orderHandleLog.setOrderNumber(orderNumber);
        orderHandleLog.setSysUserId(sysUserId);
        orderHandleLog.setIpAddress(ipAddress);
        orderHandleLog.setBeforeStatus(beforeStatus);
        orderHandleLog.setAfterStatus(afterStatus);
        orderHandleLog.setCreateTime(new Date());
        return orderHandleLogMapper.insertSelective(orderHandleLog);
    }
}
