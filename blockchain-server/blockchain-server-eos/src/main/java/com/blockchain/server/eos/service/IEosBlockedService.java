package com.blockchain.server.eos.service;


import com.blockchain.server.eos.entity.EosWalletBlockedDetail;
import com.blockchain.server.eos.entity.EosWalletBlockedTotal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:39
 */
public interface IEosBlockedService {
    //冻结余额
    void blockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //解冻余额
    void unblockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //根据账号查询冻结历史记录
    List<EosWalletBlockedDetail> listDetails(String username, String type);

    //查询总记录表
    EosWalletBlockedTotal selectTotal(String walletUid, String tokenSymbol);
}
