package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.marketuserhandlelog.ListMarketUserHandleLogResultDTO;
import com.blockchain.server.otc.entity.MarketUserHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MarketUserHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketUserHandleLogMapper extends Mapper<MarketUserHandleLog> {

    /***
     * 查询市商用户操作日志列表
     * @param userId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketUserHandleLogResultDTO> list(@Param("userId") String userId,
                                                @Param("beginTime") String beginTime,
                                                @Param("endTime") String endTime);
}