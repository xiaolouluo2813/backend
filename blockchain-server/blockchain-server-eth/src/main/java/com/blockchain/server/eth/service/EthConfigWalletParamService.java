package com.blockchain.server.eth.service;

import com.blockchain.common.base.dto.wallet.GasDTO;
import com.blockchain.server.eth.dto.wallet.ConfigWalletParamDto;
import com.blockchain.server.eth.entity.ConfigWalletParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Liusd
 * @create: 2019-03-22 11:52
 **/
public interface EthConfigWalletParamService {
    /** 
    * @Description: 提现手续费列表 
    * @Param: [] 
    * @return: java.util.List<com.blockchain.server.eth.dto.wallet.ConfigWalletParamDto> 
    * @Author: Liu.sd 
    * @Date: 2019/3/22 
    */ 
    List<ConfigWalletParamDto> ethConfigWalletParamList();

    /** 
    * @Description: 修改提现手续费
    * @Param: [gasPrice, minWdAmount, id] 
    * @return: void 
    * @Author: Liu.sd 
    * @Date: 2019/3/22 
    */ 
    void updateConfigWallet(BigDecimal gasPrice, BigDecimal minWdAmount, Integer id);
}
