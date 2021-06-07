package com.blockchain.server.quantized.mapper;

import com.blockchain.server.quantized.entity.QuantizedSymbol;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface SymbolMapper extends Mapper<QuantizedSymbol> {
}
