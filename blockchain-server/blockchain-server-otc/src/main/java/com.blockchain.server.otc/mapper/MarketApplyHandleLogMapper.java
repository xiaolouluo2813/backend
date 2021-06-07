package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.marketapplyhandlelog.ListMarketApplyHandleLogResultDTO;
import com.blockchain.server.otc.entity.MarketApplyHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MarketApplyHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketApplyHandleLogMapper extends Mapper<MarketApplyHandleLog> {

    /***
     * 查询申请操作日志列表
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketApplyHandleLogResultDTO> list(@Param("beginTime") String beginTime,
                                                 @Param("endTime") String endTime);
}