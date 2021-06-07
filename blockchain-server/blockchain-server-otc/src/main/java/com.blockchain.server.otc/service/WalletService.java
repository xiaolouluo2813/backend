package com.blockchain.server.otc.service;

import java.math.BigDecimal;

public interface WalletService {

    /***
     * 冻结、解冻余额
     * @param userId
     * @param publishId 订单id
     * @param coinName 币种
     * @param unitname 二级货币
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     */
    void handleBalance(String userId, String publishId, String coinName, String unitname,
                       BigDecimal freeBalance, BigDecimal freezeBalance);

    /***
     * 扣除、增加真实余额
     * @param userId
     * @param detailId 成交记录id
     * @param coinName 币种
     * @param unitname 二级货币
     * @param freeBalance 可用余额
     * @param freezeBalance 冻结余额
     * @param gasBalance 手续费
     */
    void handleRealBalance(String userId, String detailId, String coinName, String unitname,
                           BigDecimal freeBalance, BigDecimal freezeBalance, BigDecimal gasBalance);
}
