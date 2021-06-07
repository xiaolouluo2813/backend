package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.EosClearingCountDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthClearingCountDetail 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface EosClearingCountDetailMapper extends Mapper<EosClearingCountDetail> {
}