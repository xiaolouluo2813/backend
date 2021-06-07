package com.blockchain.server.btc.service.impl;

import com.blockchain.server.btc.entity.ConfigWalletParam;
import com.blockchain.server.btc.mapper.ConfigWalletParamMapper;
import com.blockchain.server.btc.service.IConfigWalletParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

@Service
public class ConfigWalletParamServiceImpl implements IConfigWalletParamService {

    @Autowired
    ConfigWalletParamMapper configWalletParamMapper;

    public static final String PARAM_BTC_GASWALLET = "btc_gas_wallet"; // btc油费钱包

    @Override
    public String getBtcGas() {
        return configWalletParamMapper.selectOne(where(PARAM_BTC_GASWALLET)).getParamValue();
    }

    /**
     * 条件方法
     *
     * @param paramName
     * @return
     */
    private ConfigWalletParam where(String paramName) {
        ConfigWalletParam configWalletParam = new ConfigWalletParam();
        configWalletParam.setParamName(paramName);
        return configWalletParam;
    }
}
