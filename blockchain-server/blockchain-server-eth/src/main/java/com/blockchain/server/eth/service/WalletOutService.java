package com.blockchain.server.eth.service;

import com.blockchain.server.eth.dto.wallet.EthWalletOutDto;
import com.blockchain.server.eth.entity.EthWalletTransfer;

import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-27 09:27
 **/
public interface WalletOutService {
    List<EthWalletOutDto> list(String status);

    int insert(String addr, String tokenAddr, String privateKey, String remark);

    int delete(String id);

    /**
     * 转账
     *
     * @param tx 记录
     * @return
     */
    String blockTransfer(EthWalletTransfer tx);
}
