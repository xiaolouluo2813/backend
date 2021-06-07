package com.blockchain.server.btc.mapper;

import com.blockchain.server.btc.entity.BtcClearingTotal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthClearingTotalMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface BtcClearingTotalMapper extends Mapper<BtcClearingTotal> {

    /**
     * 查询最新的资金记录
     *
     * @param addr        钱包地址
     * @param tokenSymbol 代币名称
     * @param walletType  钱包类型
     * @return
     */
    BtcClearingTotal selectNewestOne(@Param("addr") String addr,
                                     @Param("tokenSymbol") String tokenSymbol,
                                     @Param("walletType") String walletType);
}