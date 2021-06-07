package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.appeal.ListAppealResultDTO;
import com.blockchain.server.otc.entity.Appeal;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * AppealMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:21
 */
@Repository
public interface AppealMapper extends Mapper<Appeal> {

    /***
     * 查询申诉列表
     * @param orderNumber
     * @param status
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ListAppealResultDTO> listAppeal(@Param("orderNumber") String orderNumber, @Param("status") String status,
                                         @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}