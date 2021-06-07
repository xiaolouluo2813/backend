package com.blockchain.server.eth.service.impl;


import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.ExceptionPreconditionUtils;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.EthClearingCorr;
import com.blockchain.server.eth.entity.EthClearingCountTotal;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.mapper.EthClearingCorrMapper;
import com.blockchain.server.eth.mapper.EthWalletMapper;
import com.blockchain.server.eth.service.IEthClearingCorrService;
import com.blockchain.server.eth.service.IEthClearingTotalService;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class EthClearingCorrServiceImpl implements IEthClearingCorrService {
    @Autowired
    EthClearingCorrMapper ethClearingCorrMapper;

    @Override
    public List<EthClearingCorr> selectByTotalId(String totalId) {
        EthClearingCorr corr = new EthClearingCorr();
        corr.setTotalId(totalId);
        return ethClearingCorrMapper.select(corr);
    }

    @Override
    public void insert(EthClearingCorr ethClearingCorr) {
        SessionUserDTO user = SecurityUtils.getUser();
        ethClearingCorr.setSystemUserId( null != user ? user.getId(): WalletConstant.SAVE);
        ethClearingCorr.setId(UUID.randomUUID().toString());
        int row = ethClearingCorrMapper.insert(ethClearingCorr);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
