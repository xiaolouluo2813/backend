package com.blockchain.server.eos.service;

import com.blockchain.server.eos.entity.WalletIn;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-26 16:24
 **/
public interface WalletInService {
    List<WalletIn> list(String status);

    int insert(String accountName, String tokenName, String remark);

    int update(String accountName, String tokenName, String remark, String status, String id);

    int delete(String id);
}
