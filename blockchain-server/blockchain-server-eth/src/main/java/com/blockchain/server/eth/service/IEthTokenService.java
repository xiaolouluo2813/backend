package com.blockchain.server.eth.service;

import com.blockchain.server.eth.entity.EthToken;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthTokenService {
    /**
     * 根据币种名称获取以太坊的币种信息
     *
     * @param tokenName 币种名称
     * @return
     */
    EthToken findByTokenName(String tokenName);

    /**
     * 查询所有币种信息
     *
     * @return
     */
    List<EthToken> selectAll();

    EthToken findByTokenAddr(String tokenAddr);
}
