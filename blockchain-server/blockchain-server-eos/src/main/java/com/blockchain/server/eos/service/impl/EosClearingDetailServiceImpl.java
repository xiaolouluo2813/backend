package com.blockchain.server.eos.service.impl;


import com.blockchain.server.eos.common.enums.EosWalletEnums;
import com.blockchain.server.eos.common.exception.EosWalletException;
import com.blockchain.server.eos.constants.tx.DetailTxConstants;
import com.blockchain.server.eos.entity.EosClearingDetail;
import com.blockchain.server.eos.entity.EosClearingTotal;
import com.blockchain.server.eos.mapper.EosClearingDetailMapper;
import com.blockchain.server.eos.service.IEosClearingDetailService;
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
public class EosClearingDetailServiceImpl implements IEosClearingDetailService {

    @Autowired
    EosClearingDetailMapper eosClearingDetailMapper;

    @Override
    public List<EosClearingDetail> selectByTotalId(String totalId) {
        EosClearingDetail detail = new EosClearingDetail();
        detail.setTotalId(totalId);
        return eosClearingDetailMapper.select(detail);
    }

    @Override
    @Transactional
    public void insert(EosClearingTotal total, Map<String, Map<String, BigDecimal>> fromMap, Map<String, Map<String,
            BigDecimal>> toMap) {
        insert(total, fromMap, DetailTxConstants.FROM);
        insert(total, toMap, DetailTxConstants.TO);
    }

    @Override
    @Transactional
    public void insert(EosClearingTotal total, Map<String, Map<String, BigDecimal>> map, String type) {
        for (Map.Entry<String, Map<String, BigDecimal>> tokenMap : map.entrySet()) {
            for (Map.Entry<String, BigDecimal> dataMap : tokenMap.getValue().entrySet()) {
                EosClearingDetail detail = new EosClearingDetail();
                detail.setId(UUID.randomUUID().toString());
                detail.setTotalId(total.getId());
                detail.setCreateTime(total.getCreateTime());
                BigDecimal transferAmount = type.equalsIgnoreCase(DetailTxConstants.FROM) ?
                        dataMap.getValue().negate() : dataMap.getValue();
                detail.setTransferAmount(transferAmount);
                detail.setTransferType(dataMap.getKey());
                int row = eosClearingDetailMapper.insert(detail);
                if (row == 0) {
                    throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
                }
            }
        }
    }

    @Override
    @Transactional
    public void insert(String totalId, String txType, BigDecimal amount, Date endDate) {
        EosClearingDetail detail = new EosClearingDetail();
        detail.setId(UUID.randomUUID().toString());
        detail.setTotalId(totalId);
        detail.setTransferType(txType);
        detail.setTransferAmount(amount);
        detail.setCreateTime(endDate);
        int row = eosClearingDetailMapper.insert(detail);
        if (row == 0) {
            throw new EosWalletException(EosWalletEnums.SERVER_IS_TOO_BUSY);
        }
    }

}
