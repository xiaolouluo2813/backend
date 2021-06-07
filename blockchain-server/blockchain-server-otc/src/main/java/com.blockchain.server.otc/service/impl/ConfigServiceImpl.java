package com.blockchain.server.otc.service.impl;

import com.blockchain.server.otc.common.enums.ConfigEnums;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.dto.config.ListConfigResultDTO;
import com.blockchain.server.otc.dto.confighandlelog.InsertConfigHandleLogParamDTO;
import com.blockchain.server.otc.entity.Config;
import com.blockchain.server.otc.entity.ConfigHandleLog;
import com.blockchain.server.otc.mapper.ConfigMapper;
import com.blockchain.server.otc.service.ConfigHandleLogService;
import com.blockchain.server.otc.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ConfigHandleLogService configHandleLogService;

    @Override
    public List<ListConfigResultDTO> listConfig() {
        return configMapper.listConfig();
    }

    @Override
    public List<ListConfigResultDTO> listConfig(String[] dataKeys) {
        return configMapper.listConfigByKeys(dataKeys);
    }

    @Override
    @Transactional
    public int updateConfig(String sysUserId, String ipAddress, String id, String dataValue, String status) {
        //查询配置信息
        Config config = configMapper.selectByPrimaryKey(id);
        //判空
        if (config == null) {
            throw new OtcException(OtcEnums.CONFIG_NULL);
        }
        //构造配置操作日志数类
        InsertConfigHandleLogParamDTO configHandleLogDTO = new InsertConfigHandleLogParamDTO();
        //配置值不为空时
        if (StringUtils.isNotBlank(dataValue)) {
            //记录修改前后的值
            configHandleLogDTO.setBeforeValue(config.getDataValue());
            configHandleLogDTO.setAfterValue(dataValue);
            //设值
            config.setDataValue(dataValue);
        }
        //配置状态不为空时
        if (StringUtils.isNotBlank(status)) {
            //记录修改前后的值
            configHandleLogDTO.setBeforeStatus(config.getStatus());
            configHandleLogDTO.setAfterStatus(status);
            //设值
            config.setStatus(status);
        }

        //新增配置信息操作日志
        configHandleLogDTO.setDataKey(config.getDataKey());
        configHandleLogDTO.setSysUserId(sysUserId);
        configHandleLogDTO.setIpAddress(ipAddress);
        configHandleLogService.insertConfigHandleLog(configHandleLogDTO);

        //更新配置
        config.setModifyTime(new Date());
        return configMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public String selectMarketFreezeCoin() {
        Config config = configMapper.selectByKey(ConfigEnums.MARKET_FREEZE_COIN.getValue());
        if (config != null) {
            return config.getDataValue();
        }
        return null;
    }

    @Override
    public BigDecimal selectMarketFreezeAmount() {
        Config config = configMapper.selectByKey(ConfigEnums.MARKET_FREEZE_AMOUNT.getValue());
        if (config != null) {
            return new BigDecimal(config.getDataValue());
        }
        return BigDecimal.ZERO;
    }
}
