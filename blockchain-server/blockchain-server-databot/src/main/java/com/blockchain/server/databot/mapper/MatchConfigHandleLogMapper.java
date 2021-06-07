package com.blockchain.server.databot.mapper;

import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigHandleLogResultDTO;
import com.blockchain.server.databot.entity.MatchConfigHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * MatchConfigHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-06-25 13:52:32
 */
@Repository
public interface MatchConfigHandleLogMapper extends Mapper<MatchConfigHandleLog> {

    /***
     * 查询操作列表
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListMatchConfigHandleLogResultDTO> listHandleLog(@Param("handleType") String handleType,
                                                          @Param("beginTime") String beginTime,
                                                          @Param("endTime") String endTime);
}