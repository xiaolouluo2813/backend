package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.ad.ListAdResultDTO;
import com.blockchain.server.otc.entity.Ad;

import java.util.List;

public interface AdService {

    /***
     * 根据id查询广告
     * @param adId
     * @return
     */
    Ad selectById(String adId);

    /***
     * 根据id查询广告-使用排他锁
     * @param adId
     * @return
     */
    Ad selectByIdForUpdate(String adId);

    /***
     * 更新广告
     * @param ad
     * @return
     */
    int updateByPrimaryKeySelective(Ad ad);

    /***
     * 查询广告列表
     * @param adNumber
     * @param userName
     * @param coinName
     * @param unitName
     * @param adType
     * @param adStatus
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListAdResultDTO> listAd(String adNumber, String userName, String coinName,
                                 String unitName, String adType, String adStatus,
                                 String beginTime, String endTime);

    /***
     * 撤销广告
     * @param sysUserId
     * @param ipAddress
     * @param adId
     */
    void cancelAd(String sysUserId, String ipAddress, String adId);
}
