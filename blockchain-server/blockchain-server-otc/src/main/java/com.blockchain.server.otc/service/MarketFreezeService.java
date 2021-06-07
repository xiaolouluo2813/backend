package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.marketfreeze.ListMarketFreezeResultDTO;
import com.blockchain.server.otc.entity.MarketFreeze;

import java.math.BigDecimal;
import java.util.List;

public interface MarketFreezeService {

    /***
     * 查询保证金列表
     * @param userName
     * @return
     */
    List<ListMarketFreezeResultDTO> list(String userName);

    /***
     * 新增保证金记录
     * @param userId
     * @param applyId
     * @param amount
     * @param coin
     * @return
     */
    int insertMarketFreeze(String userId, String applyId, BigDecimal amount, String coin);

    /***
     * 删除保证金记录
     * @param userId
     * @return
     */
    int deleteMarketFreeze(String userId);

    /***
     * 根据用户id查询
     * @param userId
     * @return
     */
    MarketFreeze getByUserId(String userId);
}
