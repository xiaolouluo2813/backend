package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.entity.PublishOrderLog;
import com.blockchain.server.cct.mapper.PublishOrderLogMapper;
import com.blockchain.server.cct.service.PublishOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PublishOrderLogServiceImpl implements PublishOrderLogService {

    @Autowired
    private PublishOrderLogMapper orderLogMapper;

    @Override
    @Transactional
    public int insertOrderLog(PublishOrderLog orderLog) {
        return orderLogMapper.insertSelective(orderLog);
    }

    @Override
    public List<PublishOrderLog> listOrderLog() {
        return orderLogMapper.listAllOrderByTimeDESC();
    }
}
