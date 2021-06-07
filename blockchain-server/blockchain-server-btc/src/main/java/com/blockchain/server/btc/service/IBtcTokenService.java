package com.blockchain.server.btc.service;

import com.blockchain.server.btc.entity.BtcToken;

import java.util.List;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IBtcTokenService {
    /**
     * 根据币种名称获取以太坊的币种信息
     *
     * @param tokenName 币种名称
     * @return
     */
    BtcToken findByTokenName(String tokenName);

    /**
     * 根据主键获取以太坊的币种信息
     *
     * @param tokenId 主键
     * @return
     */
    BtcToken findByTokenId(Integer tokenId);

    /**
     * 查询所有币种信息
     *
     * @return
     */
    List<BtcToken> selectAll();
}
