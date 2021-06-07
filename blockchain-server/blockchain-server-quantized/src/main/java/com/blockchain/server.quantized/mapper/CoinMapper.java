package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.QuantizedCoin;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface CoinMapper extends Mapper<QuantizedCoin> {
}
