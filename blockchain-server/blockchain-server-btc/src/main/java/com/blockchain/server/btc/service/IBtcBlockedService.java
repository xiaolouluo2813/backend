package com.blockchain.server.btc.service;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.server.btc.entity.BtcWalletBlockedDetail;
import com.blockchain.server.btc.entity.BtcWalletBlockedTotal;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huangxl
 * @create 2019-06-25 11:39
 */
public interface IBtcBlockedService {
    //冻结余额
    void blockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //解冻余额
    void unblockBalance(String walletUid, String tokenSymbol, BigDecimal optNumber, String remark);

    //根据账号查询冻结历史记录
    List<BtcWalletBlockedDetail> listDetails(String username,String type);

    //查询总记录表
    BtcWalletBlockedTotal selectTotal(String walletUid, String tokenSymbol);
}
