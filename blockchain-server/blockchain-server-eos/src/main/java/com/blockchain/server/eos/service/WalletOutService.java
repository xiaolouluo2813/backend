package com.blockchain.server.eos.service;

import com.blockchain.server.eos.dto.WalletOutDTO;
import com.blockchain.server.eos.entity.WalletTransfer;
import com.blockchain.server.eos.eos4j.api.vo.transaction.Transaction;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-26 17:41
 **/
public interface WalletOutService {
    List<WalletOutDTO> list(String status);

    int update(String accountName, String tokenName, String remark, String status, String id);

    int delete(String id);

    int insert(String accountName, String tokenName, String privateKey, String remark);

    Transaction blockTransfer(WalletTransfer tx);
}
