package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.entity.QuantizedAccount;
import com.blockchain.server.quantized.mapper.AccountMapper;
import com.blockchain.server.quantized.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-25 16:12
 **/
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public QuantizedAccount findOne() {
        return accountMapper.selectAll().get(0);
    }

    @Override
    public List<QuantizedAccount> list() {
        return accountMapper.selectAll();
    }

    @Override
    @Transactional
    public int update(String id, String apiKey, String secretKey) {
        QuantizedAccount account = accountMapper.selectByPrimaryKey(id);
        if(account==null){
            throw new QuantizedException(QuantizedResultEnums.ACCOUNT_NOT_EXIST);
        }
        account.setApiKey(apiKey);
        account.setSecretKey(secretKey);
        account.setModifyTime(new Date());
        return accountMapper.updateByPrimaryKey(account);
    }
}
