package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.marketapply.ListMarketApplyResultDTO;

import java.util.List;

public interface MarketApplyService {

    /***
     * 查询市商申请列表
     * @param userName
     * @param coinName
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMarketApplyResultDTO> list(String userName, String coinName, String status, String beginTime, String endTime);

    /***
     * 同意申请
     * @param id
     * @param sysUserId
     * @param ipAddress
     */
    void agreeApply(String id, String sysUserId, String ipAddress);

    /***
     * 驳回申请
     * @param id
     * @param sysUserId
     * @param ipAddress
     */
    void rejectApply(String id, String sysUserId, String ipAddress);

    /***
     * 根据id查询市商申请列表
     * @param id
     * @return
     */
    ListMarketApplyResultDTO getById(String id);
}
