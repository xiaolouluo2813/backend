package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.entity.Application;
import com.blockchain.server.eos.mapper.ApplicationMapper;
import com.blockchain.server.eos.service.IEosApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EOS币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EosApplicationServiceImpl implements IEosApplicationService {

    @Autowired
    ApplicationMapper ethApplicationMapper;

    @Override
    public void CheckWalletType(String appId) {
        ExceptionPreconditionUtils.checkStringNotBlank(appId, new EosWalletException(EosWalletEnums.NULL_WALLETTYPE));
        List<Application> list = selectAll();
        for (Application row : list) {
            if (appId.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLETTYPE);
    }


    @Override
    public List<Application> selectAll() {
        return ethApplicationMapper.selectAll();
    }
}
