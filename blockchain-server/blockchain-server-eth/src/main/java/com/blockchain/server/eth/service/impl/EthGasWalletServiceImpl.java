package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.util.RSACoderUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.entity.EthGasWallet;
import com.blockchain.server.eth.mapper.EthGasWalletMapper;
import com.blockchain.server.eth.service.IEthGasWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthGasWalletServiceImpl implements IEthGasWalletService {

    @Autowired
    EthGasWalletMapper ethGasWalletMapper;


    @Override
    public List<EthGasWallet> select() {
        List<EthGasWallet> list = ethGasWalletMapper.selectAll();
        return list;
    }

    @Override
    public void inser(String addr, String pk) {
        Date date = new Date();
        EthGasWallet ethGasWallet = new EthGasWallet();
        ethGasWallet.setAddr(addr);
        ethGasWallet.setPrivateKey(RSACoderUtils.encryptPassword(pk));
        ethGasWallet.setCreateTime(date);
        ethGasWallet.setUpdateTime(date);
        ethGasWalletMapper.insert(ethGasWallet);
    }


}
