package com.blockchain.server.quantized.service;

import com.blockchain.server.quantized.entity.QuantizedOrderInfo;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-04-30 17:48
 **/
public interface QuantizedOrderInfoService {

    List<QuantizedOrderInfo> list(String cctId, String status);

    void rehandle(String id);
}
