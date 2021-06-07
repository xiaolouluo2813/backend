package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthClearingDetail;
import com.blockchain.server.eth.entity.EthClearingTotal;
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
public interface EthClearingTotalMapper extends Mapper<EthClearingTotal> {

    /**
     * 查询最新的资金记录
     *
     * @param addr        钱包地址
     * @param tokenSymbol 代币名称
     * @param walletType  钱包类型
     * @return
     */
    EthClearingTotal selectNewestOne(@Param("addr") String addr,
                                     @Param("tokenSymbol") String tokenSymbol,
                                     @Param("walletType") String walletType);
}