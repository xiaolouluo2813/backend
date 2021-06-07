package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.entity.ConfigLog;
import com.blockchain.server.cct.mapper.ConfigLogMapper;
import com.blockchain.server.cct.service.ConfigLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConfigLogServiceImpl implements ConfigLogService {

    @Autowired
    private ConfigLogMapper logMapper;

    @Override
    @Transactional
    public int insertConfigLog(ConfigLog log) {
        return logMapper.insertSelective(log);
    }

    @Override
    public List<ConfigLog> listConfigLog() {
        return logMapper.listConfigLogOrderByTimeDESC();
    }
}
