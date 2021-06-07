package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.coinhandlelog.ListCoinHandleLogResultDTO;
import com.blockchain.server.otc.entity.CoinHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * CoinHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface CoinHandleLogMapper extends Mapper<CoinHandleLog> {

    /***
     * 查询币种操作日志列表
     * @param coinId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListCoinHandleLogResultDTO> listCoinHandleLog(@Param("coinId") String coinId, @Param("beginTime") String beginTime,
                                                       @Param("endTime") String endTime);
}