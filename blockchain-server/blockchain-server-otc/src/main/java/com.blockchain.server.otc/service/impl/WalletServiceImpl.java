package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.WalletChangeDTO;
import com.blockchain.common.base.dto.WalletOrderDTO;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.entity.Coin;
import com.blockchain.server.otc.feign.BTCFeign;
import com.blockchain.server.otc.feign.EOSFeign;
import com.blockchain.server.otc.feign.ETHFeign;
import com.blockchain.server.otc.service.CoinService;
import com.blockchain.server.otc.service.WalletService;
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
    @Autowired
    private CoinService coinService;

    //主网标识
    private static final String BTC_NET = "BTC";
    private static final String ETH_NET = "ETH";
    private static final String EOS_NET = "EOS";

    //币币交易调用钱包Feign调用应用标识
    private static final String C2C_APP = "C2C";

    @Override
    @Transactional
    public void handleBalance(String userId, String publishId, String coinName, String unitname,
                              BigDecimal freeBalance, BigDecimal freezeBalance) {
        WalletOrderDTO order = new WalletOrderDTO();
        order.setUserId(userId);
        order.setRecordId(publishId);
        order.setTokenName(coinName);
        order.setWalletType(C2C_APP);
        order.setFreeBalance(freeBalance);
        order.setFreezeBalance(freezeBalance);
        LOG.info("调用钱包Feign，入参： order :" + order.toString() + " publishId : " + publishId + " coinName : " + coinName +
                " unitName : " + unitname + " freeBalance : " + freeBalance + " freezeBalance : " + freezeBalance);
        Coin coin = coinService.selectByCoinAndUnit(coinName, unitname);
        //币种信息判空
        if (coin == null) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }
        //根据主网标识，区分微服务调用
        switch (coin.getCoinNet()) {
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
                LOG.error("更新余额失败，钱包处理出现未知主网标识！");
                throw new OtcException(OtcEnums.WALLET_COIN_NET_ERROR);
        }
    }

    @Override
    @Transactional
    public void handleRealBalance(String userId, String recordId, String coinName, String unitname,
                                  BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance) {
        WalletChangeDTO change = new WalletChangeDTO();
        change.setUserId(userId);
        change.setRecordId(recordId);
        change.setTokenName(coinName);
        change.setFreeBalance(freeBalance);
        change.setFreezeBalance(freezeBalance);
        change.setGasBalance(gasBalance);
        change.setWalletType(C2C_APP);
        LOG.info("调用钱包Feign，入参： change :" + change.toString() + " recordId : " + recordId + " coinName : " + coinName +
                " unitName : " + unitname + " freeBalance : " + freeBalance + " freezeBalance : " + freeBalance + " gasBalance : " + gasBalance);
        Coin coin = coinService.selectByCoinAndUnit(coinName, unitname);
        //币种信息判空
        if (coin == null) {
            throw new OtcException(OtcEnums.COIN_NULL);
        }
        //根据主网标识，区分微服务调用
        switch (coin.getCoinNet()) {
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
                LOG.error("扣款或加钱失败，钱包处理出现未知主网标识！");
                throw new OtcException(OtcEnums.WALLET_COIN_NET_ERROR);
        }
    }

}
