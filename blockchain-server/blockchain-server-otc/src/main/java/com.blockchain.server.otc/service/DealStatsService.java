package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.dealstats.ListDealStatsResultDTO;

import java.util.List;

public interface DealStatsService {

    /***
     * 查询用户成交数据列表
     * @param userName
     * @return
     */
    List<ListDealStatsResultDTO> listDealStats(String userName);
}
