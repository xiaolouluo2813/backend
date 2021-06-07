package com.blockchain.server.eth.service;

import com.blockchain.server.eth.dto.wallet.EthWalletDTO;
import com.blockchain.server.eth.entity.EthClearingCorr;
import com.blockchain.server.eth.entity.EthClearingTotal;

import java.util.Date;
import java.util.List;

/**
 * 更正记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthClearingCorrService {

    /**
     * 查询统计记录的更正记录
     *
     * @param totalId
     * @return
     */
    List<EthClearingCorr> selectByTotalId(String totalId);

    /**
     * 插入一条更正数据
     *
     * @param ethClearingCorr
     */
    void insert(EthClearingCorr ethClearingCorr);


}
