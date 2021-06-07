package com.blockchain.server.eos.service;

import com.blockchain.server.eos.entity.Token;

import java.util.List;

/**
 * 以太坊币种表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEosTokenService {
    /**
     * 根据币种名称获取以太坊的币种信息
     *
     * @param tokenSymbol 币种名称
     * @return
     */
    Token findByTokenSymbol(String tokenSymbol);

    /**
    * @Description: 根据币种id获取以太坊的币种信息
    * @Param: [tokenName]
    * @return: com.blockchain.server.eos.entity.Token
    * @Author: Liu.sd
    * @Date: 2019/3/27
    */
    Token findByTokenId(String tokenId);

    /**
     * 获取币种信息
     *
     * @return
     */
    List<Token> selectAll();
}
