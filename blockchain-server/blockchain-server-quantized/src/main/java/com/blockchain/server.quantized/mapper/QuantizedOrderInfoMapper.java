package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.QuantizedOrderInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface QuantizedOrderInfoMapper extends Mapper<QuantizedOrderInfo> {
}
