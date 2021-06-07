package com.blockchain.server.quantized.service.impl;

import com.blockchain.server.quantized.common.constant.QuantizedOrderInfoConstant;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import com.blockchain.server.quantized.mapper.QuantizedOrderInfoMapper;
import com.blockchain.server.quantized.service.QuantizedOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-30 17:49
 **/
@Service
public class QuantizedOrderInfoServiceImpl implements QuantizedOrderInfoService {

    @Autowired
    QuantizedOrderInfoMapper quantizedOrderInfoMapper;

    @Override
    public List<QuantizedOrderInfo> list(String cctId, String status) {
        QuantizedOrderInfo quantizedOrderInfo = new QuantizedOrderInfo();
        if (cctId!=null && !quantizedOrderInfo.equals("")){
            quantizedOrderInfo.setCctId(cctId);
        }
        if (status!=null && !quantizedOrderInfo.equals("")){
            quantizedOrderInfo.setStatus(status);
        }
        return quantizedOrderInfoMapper.select(quantizedOrderInfo);
    }

    @Override
    public void rehandle(String id) {
        QuantizedOrderInfo quantizedOrderInfo = quantizedOrderInfoMapper.selectByPrimaryKey(id);
        if (quantizedOrderInfo == null){
            throw new QuantizedException(QuantizedResultEnums.ORDER_INFO_NOT_EXIST);
        }
        quantizedOrderInfo.setStatus(QuantizedOrderInfoConstant.STATUS_N);
        quantizedOrderInfo.setTimes(QuantizedOrderInfoConstant.DEFAULT_TIMES);
        quantizedOrderInfo.setDie(QuantizedOrderInfoConstant.DEFAULT_DIE);
        quantizedOrderInfoMapper.updateByPrimaryKey(quantizedOrderInfo);
    }
}
