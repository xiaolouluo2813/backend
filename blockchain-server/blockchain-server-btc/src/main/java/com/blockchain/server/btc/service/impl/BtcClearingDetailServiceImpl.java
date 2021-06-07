package com.blockchain.server.btc.service.impl;


import com.blockchain.server.btc.common.constants.DetailTxConstants;
import com.blockchain.server.btc.common.enums.BtcWalletEnums;
import com.blockchain.server.btc.common.exception.BtcWalletException;
import com.blockchain.server.btc.entity.BtcClearingDetail;
import com.blockchain.server.btc.entity.BtcClearingTotal;
import com.blockchain.server.btc.mapper.BtcClearingDetailMapper;
import com.blockchain.server.btc.service.IBtcClearingDetailService;
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
public class BtcClearingDetailServiceImpl implements IBtcClearingDetailService {

    @Autowired
    BtcClearingDetailMapper btcClearingDetailMapper;

    @Override
    public List<BtcClearingDetail> selectByTotalId(String totalId) {
        BtcClearingDetail detail = new BtcClearingDetail();
        detail.setTotalId(totalId);
        return btcClearingDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(BtcClearingTotal total, Map<String, Map<String, BigDecimal>> fromMap, Map<String, Map<String,
            BigDecimal>> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(BtcClearingTotal total, Map<String, Map<String, BigDecimal>> map, String type) {
        for (Map.Entry<String, Map<String, BigDecimal>> tokenMap : map.entrySet()) {
            for (Map.Entry<String, BigDecimal> dataMap : tokenMap.getValue().entrySet()) {
                BtcClearingDetail detail = new BtcClearingDetail();
                detail.setId(UUID.randomUUID().toString());
                detail.setTotalId(total.getId());
                detail.setCreateTime(total.getCreateTime());
                BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                        dataMap.getValue().negate() : dataMap.getValue();
                detail.setTransferAmount(transferAmount);
                detail.setTransferType(dataMap.getKey());
                int row = btcClearingDetailMapper.insert(detail);
                if (row == 0) {
                    throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
                }
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        BtcClearingDetail detail = new BtcClearingDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = btcClearingDetailMapper.insert(detail);
        if (row == 0) {
            throw new BtcWalletException(BtcWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
