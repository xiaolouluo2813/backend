package com.blockchain.server.btc.service;

import com.blockchain.server.btc.dto.BtcWalletOutDTO;
import com.blockchain.server.btc.entity.BtcWalletTransfer;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-27 10:13
 **/
public interface WalletOutService {
    List<BtcWalletOutDTO> list(String tokenSymbol);

    int insert(Integer tokenId, String remark);

    int delete(String id);

    /**
     * 区块转账
     *
     * @param tx 记录
     * @return
     */
    String blockTransfer(BtcWalletTransfer tx);
}
