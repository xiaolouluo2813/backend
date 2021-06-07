package com.blockchain.server.eos.mapper;

import com.blockchain.server.eos.entity.EosClearingDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthClearingDetailMapper 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface EosClearingDetailMapper extends Mapper<EosClearingDetail> {
}