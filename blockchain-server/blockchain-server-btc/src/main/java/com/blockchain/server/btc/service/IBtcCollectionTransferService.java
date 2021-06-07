package com.blockchain.server.btc.service;


import com.blockchain.server.btc.entity.BtcCollectionTransfer;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcCollectionTransferService {

    /**
     * 插入一条数据
     * @param collectionTransfer
     */
    void insert(BtcCollectionTransfer collectionTransfer);
}
