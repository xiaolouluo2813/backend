package com.blockchain.server.eos.service.impl;


import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.mapper.TokenMapper;
import com.blockchain.server.eos.service.IEosTokenService;
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
public class EosTokenServiceImpl implements IEosTokenService {

    @Autowired
    TokenMapper eosTokenMapper;


    @Override
    public Token findByTokenSymbol(String tokenSymbol) {
        ExceptionPreconditionUtils.checkStringNotBlank(tokenSymbol,
                new EosWalletException(EosWalletEnums.NULL_TOKENADDR));
        Token where = new Token();
        where.setTokenSymbol(tokenSymbol);
        Token eosToken = null;
        try {
            eosToken = eosTokenMapper.selectOne(where);
        } catch (Exception e) {
            throw new EosWalletException(EosWalletEnums.IEXIST_TOKENADDRS);
        }
        if (eosToken == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return eosToken;
    }

    @Override
    public Token findByTokenId(String tokenId) {
        Token where = new Token();
        where.setTokenName(tokenId);
        Token eosToken = null;
        try {
            eosToken = eosTokenMapper.selectOne(where);
        } catch (Exception e) {
            throw new EosWalletException(EosWalletEnums.IEXIST_TOKENADDRS);
        }
        if (eosToken == null) {
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return eosToken;
    }


    @Override
    public List<Token> selectAll() {
        return eosTokenMapper.selectAll();
    }

}
