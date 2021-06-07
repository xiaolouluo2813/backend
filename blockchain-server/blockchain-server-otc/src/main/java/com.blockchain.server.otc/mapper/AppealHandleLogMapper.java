package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.appealhandlelog.ListAppealHandleLogResultDTO;
import com.blockchain.server.otc.entity.AppealHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppealHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface AppealHandleLogMapper extends Mapper<AppealHandleLog> {

    /***
     * 查询申诉操作日志列表
     * @param orderNumber
     * @return
     */
    List<ListAppealHandleLogResultDTO> listAppealHandleLog(@Param("orderNumber") String orderNumber, @Param("beginTime") String beginTime,
                                                           @Param("endTime") String endTime);
}