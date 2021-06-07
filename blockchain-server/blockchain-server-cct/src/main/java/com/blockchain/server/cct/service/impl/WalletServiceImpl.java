package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.cct.common.enums.CCTEnums;
import com.blockchain.server.cct.common.exception.CCTException;
import com.blockchain.server.cct.feign.BTCFeign;
import com.blockchain.server.cct.feign.EOSFeign;
import com.blockchain.server.cct.feign.ETHFeign;
import com.blockchain.server.cct.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {

    private static final Logger LOG = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    private BTCFeign btcFeign;
    @Autowired
    private EOSFeign eosFeign;
    @Autowired
    private ETHFeign ethFeign;

    //主网标识
    private static final String BTC_NET = "BTC";
    private static final String ETH_NET = "ETH";
    private static final String EOS_NET = "EOS";

    //币币交易调用钱包Feign调用应用标识
    private static final String CCT_APP = "CCT";

    @Override
    @Transactional
    public void handleBalance(String userId, String publishId, String tokenName, String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance) {
        WalletOrderDTO order = new WalletOrderDTO();
        order.setUserId(userId);
        order.setRecordId(publishId);
        order.setTokenName(tokenName);
        order.setWalletType(CCT_APP);
        order.setFreeBalance(freeBalance);
        order.setFreezeBalance(freezeBalance);

        //根据主网标识，区分微服务调用
        switch (coinNet) {
            case BTC_NET:
                btcFeign.order(order);
                break;
            case ETH_NET:
                ethFeign.order(order);
                break;
            case EOS_NET:
                eosFeign.order(order);
                break;
            default:
                LOG.error("发布订单失败，钱包处理出现未知主网标识！");
                throw new CCTException(CCTEnums.HANDLE_WALLET_ERROR);
        }
    }

    @Override
    @Transactional
    public void handleRealBalance(String userId, String recordId, String tokenName, String coinNet, BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance) {
        WalletChangeDTO change = new WalletChangeDTO();
        change.setUserId(userId);
        change.setRecordId(recordId);
        change.setTokenName(tokenName);
        change.setFreeBalance(freeBalance);
        change.setFreezeBalance(freezeBalance);
        change.setGasBalance(gasBalance);
        change.setWalletType(CCT_APP);

        //根据主网标识，区分微服务调用
        switch (coinNet) {
            case BTC_NET:
                btcFeign.change(change);
                break;
            case ETH_NET:
                ethFeign.change(change);
                break;
            case EOS_NET:
                eosFeign.change(change);
                break;
            default:
                LOG.error("操作失败，钱包处理出现未知主网标识！");
                throw new CCTException(CCTEnums.HANDLE_WALLET_ERROR);
        }
    }
}
