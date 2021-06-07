package com.blockchain.server.eth.service.impl;


import com.blockchain.server.eth.common.constants.tx.DetailTxConstants;
import com.blockchain.server.eth.common.constants.tx.TotalTxConstants;
import com.blockchain.server.eth.common.enums.EthWalletEnums;
import com.blockchain.server.eth.common.exception.EthWalletException;
import com.blockchain.server.eth.dto.tx.EthWalletTxBillDTO;
import com.blockchain.server.eth.entity.EthClearingDetail;
import com.blockchain.server.eth.entity.EthClearingTotal;
import com.blockchain.server.eth.entity.EthWallet;
import com.blockchain.server.eth.mapper.EthClearingDetailMapper;
import com.blockchain.server.eth.mapper.EthClearingTotalMapper;
import com.blockchain.server.eth.service.IEthClearingDetailService;
import com.blockchain.server.eth.service.IEthClearingTotalService;
import com.blockchain.server.eth.service.IEthWalletService;
import com.blockchain.server.eth.service.IEthWalletTransferService;
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
public class EthClearingDetailServiceImpl implements IEthClearingDetailService {

    @Autowired
    EthClearingDetailMapper ethClearingDetailMapper;

    @Override
    public List<EthClearingDetail> selectByTotalId(String totalId) {
        EthClearingDetail detail = new EthClearingDetail();
        detail.setTotalId(totalId);
        return ethClearingDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(EthClearingTotal total, Map<String, Map<String, BigDecimal>> fromMap, Map<String, Map<String,
            BigDecimal>> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(EthClearingTotal total, Map<String, Map<String, BigDecimal>> map, String type) {
        for (Map.Entry<String, Map<String, BigDecimal>> tokenMap : map.entrySet()) {
            for (Map.Entry<String, BigDecimal> dataMap : tokenMap.getValue().entrySet()) {
                EthClearingDetail detail = new EthClearingDetail();
                detail.setId(UUID.randomUUID().toString());
                detail.setTotalId(total.getId());
                detail.setCreateTime(total.getCreateTime());
                BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                        dataMap.getValue().negate() : dataMap.getValue();
                detail.setTransferAmount(transferAmount);
                detail.setTransferType(dataMap.getKey());
                int row = ethClearingDetailMapper.insert(detail);
                if (row == 0) {
                    throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
                }
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        EthClearingDetail detail = new EthClearingDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = ethClearingDetailMapper.insert(detail);
        if (row == 0) {
            throw new EthWalletException(EthWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
