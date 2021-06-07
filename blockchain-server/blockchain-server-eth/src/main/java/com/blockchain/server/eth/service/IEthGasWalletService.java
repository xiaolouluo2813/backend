package com.blockchain.server.eth.service;

import com.blockchain.server.eth.entity.EthGasWallet;

import java.util.List;

/**
 * 以太坊钱包主要信息表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthGasWalletService {

    /**
     * 查询所有的油费钱包
     *
     * @return
     */
    List<EthGasWallet> select();

    /**
     * 插入
     * @param addr
     * @param pk
     */
    void inser(String addr, String pk);
}
