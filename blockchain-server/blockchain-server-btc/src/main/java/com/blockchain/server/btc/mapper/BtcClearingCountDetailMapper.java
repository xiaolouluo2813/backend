package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.entity.BtcClearingCountDetail;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthClearingCountDetail 数据访问类
 * @date 2019-02-16 15:44:06
 * @version 1.0
 */
@Repository
public interface BtcClearingCountDetailMapper extends Mapper<BtcClearingCountDetail> {
}