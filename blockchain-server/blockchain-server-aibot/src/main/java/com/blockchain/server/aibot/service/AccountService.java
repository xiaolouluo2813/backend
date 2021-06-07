package com.blockchain.server.aibot.service;

import java.math.BigDecimal;

import com.blockchain.server.aibot.entity.TradingStrategy;

/**
 *
 * @author ljj
 * @create 2020-6-3 15:18:03
 */
public interface AccountService {

	BigDecimal getAccountBalance(TradingStrategy account, String coin);
}
