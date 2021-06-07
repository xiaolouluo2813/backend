package com.blockchain.server.eos.service;

import com.blockchain.server.eos.entity.EosClearingCorr;

import java.util.List;

/**
 * 更正记录表——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEosClearingCorrService {

    /**
     * 查询统计记录的更正记录
     *
     * @param totalId
     * @return
     */
    List<EosClearingCorr> selectByTotalId(String totalId);

    /**
     * 插入一条更正数据
     *
     * @param ethClearingCorr
     */
    void insert(EosClearingCorr ethClearingCorr);


}
