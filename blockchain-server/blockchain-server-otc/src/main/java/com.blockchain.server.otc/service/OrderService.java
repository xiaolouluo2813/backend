package com.blockchain.server.otc.service;

import com.blockchain.server.otc.dto.order.ListOrderParamDTO;
import com.blockchain.server.otc.dto.order.ListOrderResultDTO;
import com.blockchain.server.otc.entity.Order;

import java.util.List;

public interface OrderService {

    /***
     * 查询订单列表
     * @param paramDTO
     * @return
     */
    List<ListOrderResultDTO> listOrder(ListOrderParamDTO paramDTO);

    /***
     * 根据id查询订单
     * @param orderId
     * @return
     */
    ListOrderResultDTO selectDTOById(String orderId);

    /***
     * 根据流水号查询订单（程序内部调用）
     * @param orderNumber
     * @return
     */
    Order selectByOrderNumber(String orderNumber);

    /***
     * 根据流水号查询订单（用于返回页面）
     * @param orderNumber
     * @return
     */
    ListOrderResultDTO selectDTOByOrderNumber(String orderNumber);

    /***
     * 根据广告Id查询订单（用于返回页面）
     * @param adId
     * @return
     */
    List<ListOrderResultDTO> selectDTOByAdId(String adId);

    /***
     * 根据id查询订单，使用排他锁
     * @param orderId
     * @return
     */
    Order selectByIdForUpdate(String orderId);

    /***
     * 根据广告id和订单状态查询订单列表
     * @param adId
     * @param status
     * @return
     */
    List<Order> selectByAdIdAndStatus(String adId, String[] status);

    /***
     * 查询广告伞下是否还有未完成订单
     * @param adId
     * @return true存在未完成订单
     */
    boolean checkOrdersUnfinished(String adId);

    /***
     * 更新订单
     * @param order
     * @return
     */
    int updateByPrimaryKeySelective(Order order);
}
