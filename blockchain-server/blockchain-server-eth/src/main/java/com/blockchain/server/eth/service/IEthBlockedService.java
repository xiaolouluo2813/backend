package com.blockchain.server.eth.service;

import com.blockchain.server.eth.entity.EthWalletBlockedDetail;
import com.blockchain.server.eth.entity.EthWalletBlockedTotal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:39
 */
public interface IEthBlockedService {
    //冻结余额
    void blockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //解冻余额
    void unblockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //根据账号查询冻结历史记录
    List<EthWalletBlockedDetail> listDetails(String username, String type);

    //查询总记录表
    EthWalletBlockedTotal selectTotal(String walletUid, String tokenSymbol);
}
