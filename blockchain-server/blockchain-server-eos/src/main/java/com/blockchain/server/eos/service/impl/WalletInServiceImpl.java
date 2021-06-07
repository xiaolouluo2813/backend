package com.blockchain.server.eos.service.impl;

import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.entity.Token;
import com.blockchain.server.eos.entity.WalletIn;
import com.blockchain.server.eos.mapper.EosWalletInMapper;
import com.blockchain.server.eos.service.IEosTokenService;
import com.blockchain.server.eos.service.WalletInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author: Liusd
 * @create: 2019-03-26 16:24
 **/
@Service
public class WalletInServiceImpl implements WalletInService {

    @Autowired
    EosWalletInMapper eosWalletInMapper;
    @Autowired
    IEosTokenService iEosTokenService;

    @Override
    public List<WalletIn> list(String status) {
        return eosWalletInMapper.selectAll();
    }

    @Override
    public int insert(String accountName, String tokenName, String remark) {
        WalletIn walletIn = new WalletIn();
        walletIn.setId(UUID.randomUUID().toString());
        walletIn.setAccountName(accountName);
        walletIn.setTokenName(tokenName);
        walletIn.setTokenSymbol(getTokenSymbol(tokenName));
        walletIn.setRemark(remark);
        walletIn.setStatus("1");
        return eosWalletInMapper.insert(walletIn);
    }

    @Override
    public int update(String accountName, String tokenName, String remark, String status, String id) {
        WalletIn walletIn = eosWalletInMapper.selectByPrimaryKey(id);
        if (walletIn==null){
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        walletIn.setAccountName(accountName!=null?accountName:walletIn.getAccountName());
        walletIn.setTokenName(tokenName!=null?tokenName:walletIn.getTokenName());
        walletIn.setTokenSymbol(tokenName!=null?getTokenSymbol(tokenName):walletIn.getTokenSymbol());
        walletIn.setRemark(remark!=null?remark:walletIn.getRemark());
        walletIn.setStatus(status!=null?status:walletIn.getStatus());
        return eosWalletInMapper.updateByPrimaryKey(walletIn);
    }

    @Override
    public int delete(String id) {
        WalletIn walletIn = eosWalletInMapper.selectByPrimaryKey(id);
        if (walletIn==null){
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_WALLET);
        }
        return eosWalletInMapper.delete(walletIn);
    }

    /**
    * @Description: 根据token_name获取tokenSymbol
    * @Param: [tokenName]
    * @return: java.lang.String
    * @Author: Liu.sd
    * @Date: 2019/3/26
    */
    private String getTokenSymbol(String tokenName){
        Token token = iEosTokenService.findByTokenId(tokenName);
        if (token==null){
            throw new EosWalletException(EosWalletEnums.INEXISTENCE_TOKENADDR);
        }
        return token.getTokenSymbol();
    }
}
