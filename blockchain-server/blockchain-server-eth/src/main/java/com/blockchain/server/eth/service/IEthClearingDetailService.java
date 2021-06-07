package com.blockchain.server.eth.service;

import com.blockchain.server.eth.entity.EthClearingDetail;
import com.blockchain.server.eth.entity.EthClearingTotal;
import com.blockchain.server.eth.entity.EthWallet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流水账记录——业务接口
 *
 * @author YH
 * @date 2019年2月16日17:09:19
 */
public interface IEthClearingDetailService {

    /**
     * 查询统计记录的流水
     *
     * @param totalId
     * @return
     */
    List<EthClearingDetail> selectByTotalId(String totalId);

    /**
     * 插入流入与流出的财务记录
     *
     * @param total   关联记录
     * @param fromMap
     * @param toMap
     */
    void insert(EthClearingTotal total,
                Map<String, Map<String, BigDecimal>> fromMap,
                Map<String, Map<String, BigDecimal>> toMap);

    /**
     * 插入多条财务记录
     *
     * @param total 关联记录
     * @param map   数据对象
     * @param type  类型
     */
    void insert(EthClearingTotal total, Map<String, Map<String, BigDecimal>> map, String type);

    /**
     * 插入一条财务记录
     * @param totalId 统计的ID
     * @param txType 统计类型
     * @param amount 余额
     * @param endDate 结束时间
     */
    void insert(String totalId, String txType, BigDecimal amount, Date endDate);
}
