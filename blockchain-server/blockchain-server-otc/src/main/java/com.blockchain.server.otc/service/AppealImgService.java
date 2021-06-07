package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;

import java.util.List;

public interface AppealImgService {

    /***
     * 根据申诉详情的id查询申诉图片
     * @param appealDetailId
     * @return
     */
    List<ListAppealImgResultDTO> listAppealImgByAppealDetailId(String appealDetailId);
}
