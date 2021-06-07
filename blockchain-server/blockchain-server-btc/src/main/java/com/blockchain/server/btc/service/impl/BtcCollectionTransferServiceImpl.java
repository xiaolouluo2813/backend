package com.blockchain.server.btc.service.impl;


import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcCollectionTransfer;
import com.blockchain.server.btc.mapper.BtcCollectionTransferMapper;
import com.blockchain.server.btc.service.IBtcCollectionTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 以太坊钱包归集记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class BtcCollectionTransferServiceImpl implements IBtcCollectionTransferService {
    @Autowired
    BtcCollectionTransferMapper collectionTransferMapper;

    @Override
    public void insert(BtcCollectionTransfer collectionTransfer) {
        int row = collectionTransferMapper.insertSelective(collectionTransfer);
        if(row == 0){
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
