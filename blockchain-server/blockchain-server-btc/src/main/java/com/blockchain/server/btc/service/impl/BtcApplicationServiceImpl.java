package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcApplication;
import com.blockchain.server.btc.mapper.BtcApplicationMapper;
import com.blockchain.server.btc.service.IBtcApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class BtcApplicationServiceImpl implements IBtcApplicationService {

    @Autowired
    BtcApplicationMapper btcApplicationMapper;

    @Override
    public void CheckWalletType(String appId) {
        ExceptionPreconditionUtils.checkStringNotBlank(appId, new BtcWalletException(BtcWalletEnums.NULL_WALLETTYPE));
        List<BtcApplication> list = selectAll();
        for (BtcApplication row : list) {
            if (appId.equalsIgnoreCase(row.getAppId())) {
                return;
            }
        }
        throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_WALLETTYPE);
    }


    @Override
    public List<BtcApplication> selectAll() {
        return btcApplicationMapper.selectAll();
    }
}
