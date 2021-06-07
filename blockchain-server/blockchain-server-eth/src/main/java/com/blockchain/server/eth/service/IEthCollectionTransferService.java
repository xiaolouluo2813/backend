package com.blockchain.server.eth.service;


import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.entity.EthCollectionTransfer;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 以太坊钱包记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthCollectionTransferService {

    /**
     * 插入一条数据
     * @param collectionTransfer
     */
    void insert(EthCollectionTransfer collectionTransfer);
}
