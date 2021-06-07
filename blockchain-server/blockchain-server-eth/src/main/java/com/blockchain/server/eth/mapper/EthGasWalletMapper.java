package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.EthGasWallet;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * EthWalletKeyMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface EthGasWalletMapper extends Mapper<EthGasWallet> {
}