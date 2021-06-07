package com.blockchain.server.eos.service.impl;


import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.DetailTxConstants;
import com.blockchain.server.eos.entity.EosClearingCountDetail;
import com.blockchain.server.eos.entity.EosClearingCountTotal;
import com.blockchain.server.eos.mapper.EosClearingCountDetailMapper;
import com.blockchain.server.eos.service.IEosClearingCountDetailService;
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
public class EosClearingCountDetailServiceImpl implements IEosClearingCountDetailService {

    @Autowired
    EosClearingCountDetailMapper eosClearingCountDetailMapper;


    @Override
    public List<EosClearingCountDetail> selectByTotalId(String totalId) {
        EosClearingCountDetail detail = new EosClearingCountDetail();
        detail.setTotalId(totalId);
        return eosClearingCountDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(EosClearingCountTotal total, Map<String, BigDecimal> fromMap, Map<String, BigDecimal> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(EosClearingCountTotal countTotal, Map<String, BigDecimal> map, String type) {
        for (Map.Entry<String, BigDecimal> dataMap : map.entrySet()) {
            EosClearingCountDetail detail = new EosClearingCountDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setTotalId(countTotal.getId());
            detail.setCreateTime(countTotal.getCreateTime());
            BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                    dataMap.getValue().negate() : dataMap.getValue();
            detail.setTransferAmount(transferAmount);
            detail.setTransferType(dataMap.getKey());
            int row = eosClearingCountDetailMapper.insert(detail);
            if (row == 0) {
                throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        EosClearingCountDetail detail = new EosClearingCountDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = eosClearingCountDetailMapper.insert(detail);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
