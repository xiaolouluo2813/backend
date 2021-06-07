package com.blockchain.server.otc.mapper;

import com.blockchain.server.otc.dto.order.ListOrderParamDTO;
import com.blockchain.server.otc.dto.order.ListOrderResultDTO;
import com.blockchain.server.otc.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * OrderMapper 数据访问类
 *
 * @version 1.0
 * @date 2019-05-06 16:39:22
 */
@Repository
public interface OrderMapper extends Mapper<Order> {

    /***
     * 根据订单流水号查询订单
     * @param orderNumber
     * @return
     */
    Order selectByOrderNumber(@Param("orderNumber") String orderNumber);

    /***
     * 根据id查询订单使用排他锁
     * @param orderId
     * @return
     */
    Order selectByIdForUpdate(@Param("orderId") String orderId);

    /***
     * 根据广告id和订单状态查询订单信息
     * @param adId
     * @param status
     * @return
     */
    List<Order> selectByAdIdAndStatus(@Param("adId") String adId, @Param("status") String[] status);

    /***
     * 查询订单
     * @param userId
     * @param paramDTO
     * @return
     */
    List<ListOrderResultDTO> listOrder(@Param("userId") String userId, @Param("param") ListOrderParamDTO paramDTO);

    /***
     * 根据广告id查询订单
     * @param adId
     * @return
     */
    List<ListOrderResultDTO> selectDTOByAdId(String adId);

    /***
     * 根据订单id查询订单
     * @param id
     * @return
     */
    ListOrderResultDTO selectDTOById(String id);
}