package com.blockchain.server.eth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.base.dto.wallet.GasDTO;
import com.blockchain.common.base.util.JsonUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.wallet.ConfigWalletParamDto;
import com.blockchain.server.eth.entity.ConfigWalletParam;
import com.blockchain.server.eth.mapper.ConfigWalletParamMapper;
import com.blockchain.server.eth.service.EthConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-22 11:53
 **/
@Service
public class EthConfigWalletParamServiceImpl implements EthConfigWalletParamService {

    @Autowired
    ConfigWalletParamMapper configWalletParamMapper;

    @Override
    public List<ConfigWalletParamDto> ethConfigWalletParamList() {
        List<ConfigWalletParam> list = configWalletParamMapper.selectAllByParamNameContainsGasConfig();
        List<ConfigWalletParamDto> configWalletParamDtos = new ArrayList<>();
        for (ConfigWalletParam configWalletParam : list){
            ConfigWalletParamDto configWalletParamDto = new ConfigWalletParamDto();
            configWalletParamDto.setId(configWalletParam.getId());
            configWalletParamDto.setModuleType(configWalletParam.getModuleType());
            configWalletParamDto.setCreateTime(configWalletParam.getCreateTime());
            configWalletParamDto.setModifyTime(configWalletParam.getModifyTime());
            configWalletParamDto.setParamDescr(configWalletParam.getParamDescr());
            configWalletParamDto.setParamName(configWalletParam.getParamName());
            configWalletParamDto.setStatus(configWalletParam.getStatus());
            configWalletParamDto.setParamValue(JsonUtils.jsonToPojo(configWalletParam.getParamValue(), GasDTO.class));
            configWalletParamDtos.add(configWalletParamDto);
        }
        return configWalletParamDtos;
    }

    @Transactional
    @Override
    public void updateConfigWallet(BigDecimal gasPrice, BigDecimal minWdAmount, Integer id) {
        ConfigWalletParam configWalletParam = configWalletParamMapper.selectByPrimaryKey(id);
        if (configWalletParam == null){
            throw new EthWalletException(EthWalletEnums.NULL_TX);
        }
        GasDTO gasDTO = JsonUtils.jsonToPojo(configWalletParam.getParamValue(), GasDTO.class);
        gasDTO.setGasPrice(gasPrice);
        gasDTO.setMinWdAmount(minWdAmount);
        configWalletParam.setParamValue(JSONObject.toJSONString(gasDTO));
        configWalletParamMapper.updateByPrimaryKey(configWalletParam);
    }
}
