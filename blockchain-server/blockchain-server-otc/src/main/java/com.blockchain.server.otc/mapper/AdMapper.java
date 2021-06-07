package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.ad.ListAdResultDTO;
import com.blockchain.server.otc.entity.Ad;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AdMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:21
 */
@Repository
public interface AdMapper extends Mapper<Ad> {

    /***
     * 根据广告id查询广告，使用排他锁
     * @param adId
     * @return
     */
    Ad selectByIdForUpdate(@Param("adId") String adId);

    /***
     * 查询广告列表
     * @param userId
     * @param adNumber
     * @param coinName
     * @param unitName
     * @param adType
     * @param adStatus
     * @return
     */
    List<ListAdResultDTO> listAd(@Param("userId") String userId, @Param("adNumber") String adNumber,
                                 @Param("coinName") String coinName, @Param("unitName") String unitName,
                                 @Param("adType") String adType, @Param("adStatus") String adStatus,
                                 @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}