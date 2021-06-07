package com.blockchain.server.btc.service;


import com.blockchain.server.btc.dto.BtcTotalInfoDTO;
import com.blockchain.server.btc.dto.BtcWalletDTO;
import com.blockchain.server.btc.entity.BtcClearingTotal;
import com.blockchain.server.btc.entity.BtcWallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 期初期末记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcClearingTotalService {

    /**
     * 获取钱包最新的统计记录
     *
     * @param addr        钱包地址
     * @param tokenSymbol 代币名称
     * @param walletType  钱包类型
     * @return
     */
    BtcClearingTotal findNewByWallet(String addr, String tokenSymbol, String walletType);

    /**
     * 获取统计记录
     *
     * @param id
     * @return
     */
    BtcClearingTotal findById(String id);

    /**
     * 获取用户所有币种最新统计的记录
     *
     * @param userId
     */
    List<BtcTotalInfoDTO> selectInfoAll(String userId);

    /**
     * 更正统计记录的资金
     *
     * @param id     统计记录ID
     * @param amount 金额
     */
    void updateCorr(String id, BigDecimal amount);

    /**
     * 生成用户财务记录快照
     *
     * @param btcWallet 钱包对象
     * @param endDate   期末时间
     */
    void insertTotal(BtcWalletDTO btcWallet, Date endDate);

    /**
     * 生成用户财务记录快照
     *
     * @param ethWallet 钱包对象
     * @param endDate   期末时间
     */
    void insertTotal(BtcWallet ethWallet, Date endDate);

    /**
     * 生成用户财务记录快照(多个)
     *
     * @param userId 用户ID
     */
    List<BtcTotalInfoDTO> insertTotals(String userId);


    /**
     * 插入数据
     *
     * @param ethClearingTotal
     */
    void insert(BtcClearingTotal ethClearingTotal);


}
