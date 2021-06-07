package com.blockchain.server.btc.service.impl;


import com.blockchain.common.base.constant.WalletConstant;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcClearingCorr;
import com.blockchain.server.btc.mapper.BtcClearingCorrMapper;
import com.blockchain.server.btc.service.IBtcClearingCorrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * 以太坊钱包表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:25:02
 */
@Service
public class BtcClearingCorrServiceImpl implements IBtcClearingCorrService {
    @Autowired
    BtcClearingCorrMapper btcClearingCorrMapper;

    @Override
    public List<BtcClearingCorr> selectByTotalId(String totalId) {
        BtcClearingCorr corr = new BtcClearingCorr();
        corr.setTotalId(totalId);
        return btcClearingCorrMapper.select(corr);
    }

    @Override
    public void insert(BtcClearingCorr ethClearingCorr) {
        SessionUserDTO user = SecurityUtils.getUser();
        ethClearingCorr.setSystemUserId( null != user ? user.getId(): WalletConstant.SAVE);
        ethClearingCorr.setId(UUID.randomUUID().toString());
        int row = btcClearingCorrMapper.insert(ethClearingCorr);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }
}
