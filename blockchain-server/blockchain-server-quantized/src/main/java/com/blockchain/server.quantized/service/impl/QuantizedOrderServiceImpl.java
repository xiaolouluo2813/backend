package com.blockchain.server.quantized.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.dto.QuantizedOrderDto;
import com.blockchain.server.quantized.entity.QuantizedOrder;
import com.blockchain.server.quantized.feign.UserFeign;
import com.blockchain.server.quantized.mapper.QuantizedOrderMapper;
import com.blockchain.server.quantized.service.OrderService;
import com.blockchain.server.quantized.service.QuantizedOrderService;
import com.huobi.client.model.Order;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author: Liusd
 * @create: 2019-04-19 16:51
 **/
@Service
public class QuantizedOrderServiceImpl implements QuantizedOrderService {


    private static final Logger LOG = LoggerFactory.getLogger(QuantizedOrderServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private QuantizedOrderMapper quantizedOrderMapper;

    @Autowired
    UserFeign userFeign;

    @Override
    @Transactional
    public int insert(QuantizedOrder quantizedOrder) {
        LOG.info("下单成功、新增本地订单记录");
        return quantizedOrderMapper.insert(quantizedOrder);
    }

    @Override
    @Transactional
    public int Update(Order order) {
        QuantizedOrder quantizedOrder = quantizedOrderMapper.selectByPrimaryKey(order.getOrderId());
        if (quantizedOrder!=null){
            quantizedOrder.setAmount(order.getAmount().toString());
            quantizedOrder.setCreatedAt(order.getCreatedTimestamp());
            quantizedOrder.setCanceledAt(order.getCanceledTimestamp());
            quantizedOrder.setFieldAmount(order.getFilledAmount().toString());
            quantizedOrder.setFieldCashAmount(order.getFilledCashAmount().toString());
            quantizedOrder.setFieldFees(order.getFilledFees().toString());
            quantizedOrder.setFinishedAt(order.getFinishedTimestamp());
            quantizedOrder.setPrice(order.getPrice().toString());
            quantizedOrder.setSource(order.getSource().toString());
            quantizedOrder.setState(order.getState().toString());
            quantizedOrder.setSymbol(order.getSymbol());
            quantizedOrder.setType(order.getType().toString());
            return quantizedOrderMapper.updateByPrimaryKey(quantizedOrder);
        }
        return 0;
    }

    @Override
    public QuantizedOrder selectByCctId(String cctId) {
        QuantizedOrder quantizedOrder = new QuantizedOrder();
        quantizedOrder.setCctId(cctId);
        QuantizedOrder order = quantizedOrderMapper.selectOne(quantizedOrder);
        return order;
    }

    @Override
    public List<QuantizedOrderDto> list(String mobilePhone, String state, String type) {
        if (StringUtils.isNotBlank(mobilePhone)) {
            UserBaseInfoDTO user = selectUserByUserName(mobilePhone);
            if (user == null) {
                return null;
            }
            mobilePhone = user.getUserId();
        }
        Set<String> userIds = new HashSet<>();
        List<QuantizedOrderDto> list = quantizedOrderMapper.listAll(mobilePhone,state,type);
        for (QuantizedOrderDto quantizedOrder : list){
            userIds.add(quantizedOrder.getUserId());
        }
//          获取用户信息
        if (userIds.size()>0){
            ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
            if (result == null) throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
            if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
            Map<String, UserBaseInfoDTO> userMap = result.getData();
            for (QuantizedOrderDto quantizedOrder : list){
                String userId = quantizedOrder.getUserId();
                if (userMap.containsKey(userId)) quantizedOrder.setUserBaseInfoDTO(userMap.get(userId));
            }
        }
        return list;
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

}
