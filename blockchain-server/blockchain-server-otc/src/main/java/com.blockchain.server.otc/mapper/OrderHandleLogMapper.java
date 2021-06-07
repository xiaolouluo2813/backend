package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.orderhandlelog.ListOrderHandleLogResultDTO;
import com.blockchain.server.otc.entity.OrderHandleLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * OrderHandleLogMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface OrderHandleLogMapper extends Mapper<OrderHandleLog> {

    /***
     * 查询订单操作日志列表
     * @param orderNumber
     * @return
     */
    List<ListOrderHandleLogResultDTO> listOrderHandleLog(@Param("orderNumber") String orderNumber);
}