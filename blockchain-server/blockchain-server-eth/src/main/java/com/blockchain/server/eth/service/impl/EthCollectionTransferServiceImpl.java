package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.dto.wallet.WalletTxDTO;
import com.blockchain.common.base.dto.wallet.WalletTxParamsDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.server.eth.common.constants.tx.OutTxConstants;
import com.blockchain.server.eth.common.constants.tx.TxTypeConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.tx.EthWalletTransferDTO;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.entity.EthCollectionTransfer;
import com.blockchain.server.eth.entity.EthToken;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.entity.EthWalletTransfer;
import com.blockchain.server.eth.feign.UserFeign;
import com.blockchain.server.eth.mapper.EthCollectionTransferMapper;
import com.blockchain.server.eth.mapper.EthWalletTransferMapper;
import com.blockchain.server.eth.service.IEthCollectionTransferService;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import com.blockchain.server.eth.service.WalletOutService;
import com.blockchain.server.eth.web3j.IWalletWeb3j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 以太坊钱包归集记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class EthCollectionTransferServiceImpl implements IEthCollectionTransferService {
    @Autowired
    EthCollectionTransferMapper collectionTransferMapper;

    @Override
    public void insert(EthCollectionTransfer collectionTransfer) {
        int row = collectionTransferMapper.insert(collectionTransfer);
        if(row == 0){
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
