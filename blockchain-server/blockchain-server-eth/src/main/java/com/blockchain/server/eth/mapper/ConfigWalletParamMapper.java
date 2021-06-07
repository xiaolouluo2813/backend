package com.blockchain.server.eth.mapper;

import com.blockchain.server.eth.entity.ConfigWalletParam;
import com.blockchain.server.eth.entity.EthWalletOut;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * ConfigWalletParam 数据访问类
 *
 * @version 1.0
 * @date 2019-02-16 15:44:06
 */
@Repository
public interface ConfigWalletParamMapper extends Mapper<ConfigWalletParam> {

    /** 
    * @Description: 全部提现手续费列表
    * @Param: [] 
    * @return: java.util.List<com.blockchain.server.eth.entity.ConfigWalletParam> 
    * @Author: Liu.sd 
    * @Date: 2019/3/22 
    */ 
    List<ConfigWalletParam> selectAllByParamNameContainsGasConfig();
}