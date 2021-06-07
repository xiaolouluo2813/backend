package com.blockchain.server.cct.mapper;

import com.blockchain.server.cct.dto.publishOrder.ListOrderParamDTO;
import com.blockchain.server.cct.dto.publishOrder.ListOrderResultDTO;
import com.blockchain.server.cct.entity.PublishOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PublishOrderMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-03-06 11:53:27
 */
@Repository
public interface PublishOrderMapper extends Mapper<PublishOrder> {

    /***
     * 查询委托订单列表
     * @param param
     * @return
     */
    List<ListOrderResultDTO> listOrder(@Param("param") ListOrderParamDTO param);

    /***
     * 使用排他锁查询订单
     * @param orderId
     * @return
     */
    PublishOrder selectOrderForUpdate(@Param("orderId") String orderId);
}