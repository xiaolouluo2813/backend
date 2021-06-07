package com.blockchain.server.btc.service.impl;


import com.blockchain.server.btc.common.constants.DetailTxConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcClearingCountDetail;
import com.blockchain.server.btc.entity.BtcClearingCountTotal;
import com.blockchain.server.btc.mapper.BtcClearingCountDetailMapper;
import com.blockchain.server.btc.mapper.BtcClearingDetailMapper;
import com.blockchain.server.btc.service.IBtcClearingCountDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 财务详情记录——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
@Service
public class BtcClearingCountDetailServiceImpl implements IBtcClearingCountDetailService {

    @Autowired
    BtcClearingCountDetailMapper btcClearingCountDetailMapper;


    @Override
    public List<BtcClearingCountDetail> selectByTotalId(String totalId) {
        BtcClearingCountDetail detail = new BtcClearingCountDetail();
        detail.setTotalId(totalId);
        return btcClearingCountDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(BtcClearingCountTotal total, Map<String, BigDecimal> fromMap, Map<String, BigDecimal> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(BtcClearingCountTotal countTotal, Map<String, BigDecimal> map, String type) {
        for (Map.Entry<String, BigDecimal> dataMap : map.entrySet()) {
            BtcClearingCountDetail detail = new BtcClearingCountDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setTotalId(countTotal.getId());
            detail.setCreateTime(countTotal.getCreateTime());
            BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                    dataMap.getValue().negate() : dataMap.getValue();
            detail.setTransferAmount(transferAmount);
            detail.setTransferType(dataMap.getKey());
            int row = btcClearingCountDetailMapper.insert(detail);
            if (row == 0) {
                throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        BtcClearingCountDetail detail = new BtcClearingCountDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = btcClearingCountDetailMapper.insert(detail);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }




}
