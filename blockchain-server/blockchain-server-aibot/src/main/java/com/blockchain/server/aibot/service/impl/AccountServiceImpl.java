package com.blockchain.server.aibot.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.blockchain.server.aibot.entity.TradingStrategy;
import com.blockchain.server.aibot.service.AccountService;
import com.huobi.client.SyncRequestClient;
import com.huobi.client.model.Account;
import com.huobi.client.model.Balance;
import com.huobi.client.model.enums.AccountType;
import com.huobi.client.model.enums.BalanceType;

@Service
public class AccountServiceImpl implements AccountService {

	@Override
	public BigDecimal getAccountBalance(TradingStrategy account, String coin) {
		SyncRequestClient syncRequestClient = SyncRequestClient.create(account.getApiKey(), account.getSecretKey());
		Account accountBalance = syncRequestClient.getAccountBalance(AccountType.SPOT);
		List<Balance> balances = accountBalance.getBalance(coin);
		for (Balance balance : balances) {
			if (BalanceType.TRADE == balance.getType()) {
				return balance.getBalance();
			}
		}
		return null;
	}

}
