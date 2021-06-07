package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.dealstats.ListDealStatsResultDTO;
import com.blockchain.server.otc.entity.DealStats;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * DealStatsMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface DealStatsMapper extends Mapper<DealStats> {

    /***
     * 查询用户成交统计列表
     * @param userId
     * @return
     */
    List<ListDealStatsResultDTO> listDealStats(@Param("userId") String userId);
}