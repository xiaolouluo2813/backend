package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcToken;
import com.blockchain.server.btc.mapper.BtcTokenMapper;
import com.blockchain.server.btc.service.IBtcTokenService;
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
public class BtcTokenServiceImpl implements IBtcTokenService {

    @Autowired
    BtcTokenMapper btcTokenMapper;


    @Override
    public BtcToken findByTokenName(String tokenName) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenName,
                new BtcWalletException(BtcWalletEnums.NULL_TOKENADDR));
        BtcToken where = new BtcToken();
        where.setTokenSymbol(tokenName);
        BtcToken ethToken = null;
        try {
            ethToken = btcTokenMapper.selectOne(where);
        } catch (Exception e) {
            throw new BtcWalletException(BtcWalletEnums.IEXIST_TOKENADDRS);
        }
        if (ethToken == null) {
            throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return ethToken;
    }

    @Override
    public BtcToken findByTokenId(Integer tokenId) {
        BtcToken where = new BtcToken();
        where.setTokenId(tokenId);
        BtcToken ethToken = null;
        try {
            ethToken = btcTokenMapper.selectOne(where);
        } catch (Exception e) {
            throw new BtcWalletException(BtcWalletEnums.IEXIST_TOKENADDRS);
        }
        if (ethToken == null) {
            throw new BtcWalletException(BtcWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return ethToken;
    }

    @Override
    public List<BtcToken> selectAll() {
        return btcTokenMapper.selectAll();
    }

}
