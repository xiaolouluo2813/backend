package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.adhandlelog.ListAdHandleLogResultDTO;
import com.blockchain.server.otc.entity.AdHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AdHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:21
 */
@Repository
public interface AdHandleLogMapper extends Mapper<AdHandleLog> {

    /***
     * 查询广告处理日志列表
     * @param adNumber
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListAdHandleLogResultDTO> listAdHandleLog(@Param("adNumber") String adNumber, @Param("beginTime") String beginTime,
                                                   @Param("endTime") String endTime);
}