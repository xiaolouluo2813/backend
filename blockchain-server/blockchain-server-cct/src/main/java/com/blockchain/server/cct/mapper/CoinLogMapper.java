package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.entity.CoinLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PcCctCoinLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface CoinLogMapper extends Mapper<CoinLog> {

    /***
     * 查询所有交易对操作记录
     * 时间倒序
     * @return
     */
    List<CoinLog> listAllOrderByTimeDESC();
}