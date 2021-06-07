package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.marketapply.ListMarketApplyResultDTO;
import com.blockchain.server.otc.entity.MarketApply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MarketApplyMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-07-13 11:48:11
 */
@Repository
public interface MarketApplyMapper extends Mapper<MarketApply> {

    /***
     * 查询市商申请列表
     * @param userId
     * @param coinName
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketApplyResultDTO> list(@Param("userId") String userId, @Param("coinName") String coinName, @Param("status") String status,
                                        @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}