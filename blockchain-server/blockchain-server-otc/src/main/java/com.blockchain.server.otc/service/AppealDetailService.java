package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.appealdetail.ListAppealDetailResultDTO;

import java.util.List;

public interface AppealDetailService {

    /***
     * 根据申诉记录id查询用户申诉详情列表
     * @param appealId
     * @return
     */
    List<ListAppealDetailResultDTO> listAppealDetailByAppealId(String appealId);
}
