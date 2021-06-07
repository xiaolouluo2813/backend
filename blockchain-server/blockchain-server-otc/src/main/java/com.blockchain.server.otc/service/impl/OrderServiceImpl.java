package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.common.enums.OrderEnums;
import com.blockchain.server.otc.dto.order.ListOrderParamDTO;
import com.blockchain.server.otc.dto.order.ListOrderResultDTO;
import com.blockchain.server.otc.dto.userpayinfo.ListUserPayInfoResultDTO;
import com.blockchain.server.otc.entity.Order;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.OrderMapper;
import com.blockchain.server.otc.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListOrderResultDTO> listOrder(ListOrderParamDTO paramDTO) {
        if (StringUtils.isNotBlank(paramDTO.getUserName())) {
            return listOrderByUser(paramDTO);
        } else {
            return listOrderByCondition(paramDTO);
        }
    }

    /***
     * 根据用户查询订单
     * @param paramDTO
     * @return
     */
    private List<ListOrderResultDTO> listOrderByUser(ListOrderParamDTO paramDTO) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(paramDTO.getUserName());
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return orderMapper.listOrder("", paramDTO);
        }
        //查询列表
        List<ListOrderResultDTO> resultDTOS = orderMapper.listOrder(userBaseInfoDTO.getUserId(), paramDTO);
        //设置用户信息
        for (ListOrderResultDTO resultDTO : resultDTOS) {
            //查询的用户是买家
            if (resultDTO.getBuyUserId().equals(userBaseInfoDTO.getUserId())) {
                //设置买家信息
                resultDTO.setBuyUserName(userBaseInfoDTO.getMobilePhone());
                resultDTO.setBuyRealName(userBaseInfoDTO.getRealName());
                //查询卖家信息
                UserBaseInfoDTO sellUserInfo = selectUserByUserId(resultDTO.getSellUserId());
                if (sellUserInfo != null) {
                    //设置卖家信息
                    resultDTO.setSellUserName(sellUserInfo.getMobilePhone());
                    resultDTO.setSellRealName(sellUserInfo.getRealName());
                }
            }
            //查询的用户是卖家
            if (resultDTO.getSellUserId().equals(userBaseInfoDTO.getUserId())) {
                //设置卖家信息
                resultDTO.setSellUserName(userBaseInfoDTO.getMobilePhone());
                resultDTO.setSellRealName(userBaseInfoDTO.getRealName());
                //查询买家信息
                UserBaseInfoDTO buyUserInfo = selectUserByUserId(resultDTO.getBuyUserId());
                if (buyUserInfo != null) {
                    //设置买家信息
                    resultDTO.setBuyUserName(buyUserInfo.getMobilePhone());
                    resultDTO.setBuyRealName(buyUserInfo.getRealName());
                }
            }
        }

        return resultDTOS;
    }

    /***
     * 查询订单
     * @param paramDTO
     * @return
     */
    private List<ListOrderResultDTO> listOrderByCondition(ListOrderParamDTO paramDTO) {
        //查询列表
        List<ListOrderResultDTO> resultDTOS = orderMapper.listOrder("", paramDTO);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装双方用户id
        for (ListOrderResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getBuyUserId());
            userIds.add(resultDTO.getSellUserId());
        }
        //防止用户ids为空
        if (userIds.size() == 0) {
            return resultDTOS;
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return resultDTOS;
        }
        //设置用户信息
        for (ListOrderResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取双方用户数据
            UserBaseInfoDTO buyUserInfo = userInfos.get(resultDTO.getBuyUserId());
            UserBaseInfoDTO sellUserInfo = userInfos.get(resultDTO.getSellUserId());
            //防空
            if (buyUserInfo != null) {
                //设置买方用户信息
                resultDTO.setBuyUserName(buyUserInfo.getMobilePhone());
                resultDTO.setBuyRealName(buyUserInfo.getRealName());
            }
            if (sellUserInfo != null) {
                //设置卖方用户信息
                resultDTO.setSellUserName(sellUserInfo.getMobilePhone());
                resultDTO.setSellRealName(sellUserInfo.getRealName());
            }
        }
        //返回数据
        return resultDTOS;
    }

    @Override
    public ListOrderResultDTO selectDTOById(String orderId) {
        ListOrderResultDTO resultDTO = orderMapper.selectDTOById(orderId);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装双方用户id
        userIds.add(resultDTO.getBuyUserId());
        userIds.add(resultDTO.getSellUserId());
        //防止用户ids为空
        if (userIds.size() == 0) {
            return resultDTO;
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return resultDTO;
        }
        //根据用户id从map中获取双方用户数据
        UserBaseInfoDTO buyUserInfo = userInfos.get(resultDTO.getBuyUserId());
        UserBaseInfoDTO sellUserInfo = userInfos.get(resultDTO.getSellUserId());
        //防空
        if (buyUserInfo != null) {
            //设置买方用户信息
            resultDTO.setBuyUserName(buyUserInfo.getMobilePhone());
            resultDTO.setBuyRealName(buyUserInfo.getRealName());
        }
        if (sellUserInfo != null) {
            //设置卖方用户信息
            resultDTO.setSellUserName(sellUserInfo.getMobilePhone());
            resultDTO.setSellRealName(sellUserInfo.getRealName());
        }
        return resultDTO;
    }

    @Override
    public Order selectByOrderNumber(String orderNumber) {
        return orderMapper.selectByOrderNumber(orderNumber);
    }

    @Override
    public ListOrderResultDTO selectDTOByOrderNumber(String orderNumber) {
        //根据订单流水号查询订单
        Order order = orderMapper.selectByOrderNumber(orderNumber);
        //最终返回数据
        ListOrderResultDTO orderDTO = new ListOrderResultDTO();
        //复制数据到新的DTO中
        BeanUtils.copyProperties(order, orderDTO);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        userIds.add(order.getBuyUserId());
        userIds.add(order.getSellUserId());
        //查询双方用户信息
        Map<String, UserBaseInfoDTO> map = listUserInfos(userIds);
        //设置双方用户信息
        orderDTO.setBuyUserName(map.get(order.getBuyUserId()).getMobilePhone());
        orderDTO.setBuyRealName(map.get(order.getBuyUserId()).getRealName());
        orderDTO.setSellUserName(map.get(order.getSellUserId()).getMobilePhone());
        orderDTO.setSellRealName(map.get(order.getSellUserId()).getRealName());
        return orderDTO;
    }

    @Override
    public List<ListOrderResultDTO> selectDTOByAdId(String adId) {
        List<ListOrderResultDTO> resultDTOS = orderMapper.selectDTOByAdId(adId);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        for (ListOrderResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getBuyUserId());
            userIds.add(resultDTO.getSellUserId());
        }
        //防止用户ids为空
        if (userIds.size() == 0) {
            return resultDTOS;
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return resultDTOS;
        }
        //设置用户信息
        for (ListOrderResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取双方用户数据
            UserBaseInfoDTO buyUserInfo = userInfos.get(resultDTO.getBuyUserId());
            UserBaseInfoDTO sellUserInfo = userInfos.get(resultDTO.getSellUserId());
            //防空
            if (buyUserInfo != null) {
                //设置买方用户信息
                resultDTO.setBuyUserName(buyUserInfo.getMobilePhone());
                resultDTO.setBuyRealName(buyUserInfo.getRealName());
            }
            if (sellUserInfo != null) {
                //设置卖方用户信息
                resultDTO.setSellUserName(sellUserInfo.getMobilePhone());
                resultDTO.setSellRealName(sellUserInfo.getRealName());
            }
        }
        //返回数据
        return resultDTOS;
    }

    @Override
    public Order selectByIdForUpdate(String orderId) {
        return orderMapper.selectByIdForUpdate(orderId);
    }

    @Override
    public List<Order> selectByAdIdAndStatus(String adId, String[] status) {
        return orderMapper.selectByAdIdAndStatus(adId, status);
    }

    @Override
    public boolean checkOrdersUnfinished(String adId) {
        String[] orderStatus = {OrderEnums.STATUS_NEW.getValue(),
                OrderEnums.STATUS_UNDERWAY.getValue(), OrderEnums.STATUS_APPEAL.getValue()};
        //根据广告id和订单状态（新建、进行中、申诉中）查询订单列表
        List<Order> orders = this.selectByAdIdAndStatus(adId, orderStatus);
        //存在未完成订单，返回true
        if (orders.size() != 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    /***
     * 根据userName查询用户信息
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserByUserName(String userName) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfoByUserName(userName);
        return resultDTO.getData();
    }

    /***
     * 根据userId查询用户信息
     * @param userId
     * @return
     */
    private UserBaseInfoDTO selectUserByUserId(String userId) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfo(userId);
        return resultDTO.getData();
    }

    /***
     * 根据id集合查询多个用户信息
     * @param userIds
     * @return
     */
    private Map<String, UserBaseInfoDTO> listUserInfos(Set<String> userIds) {
        ResultDTO<Map<String, UserBaseInfoDTO>> resultDTO = userFeign.listUserInfo(userIds);
        return resultDTO.getData();
    }
}
