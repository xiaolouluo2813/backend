package com.blockchain.server.quantized.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.quantized.common.enums.QuantizedResultEnums;
import com.blockchain.server.quantized.common.exception.QuantizedException;
import com.blockchain.server.quantized.dto.OrderErrDto;
import com.blockchain.server.quantized.entity.OrderErr;
import com.blockchain.server.quantized.feign.UserFeign;
import com.blockchain.server.quantized.mapper.OrderErrMapper;
import com.blockchain.server.quantized.service.OrderErrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author: Liusd
 * @create: 2019-04-18 20:07
 **/
@Service
public class OrderErrServiceImpl implements OrderErrService {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private OrderErrMapper orderErrMapper;

    @Override
    @Transactional
    public int insert(OrderErr orderErr) {
        return orderErrMapper.insert(orderErr);
    }

    @Override
    public List<OrderErrDto> list() {
        Set<String> userIds = new HashSet<>();
        List<OrderErrDto> list = orderErrMapper.listAll();
        for (OrderErrDto orderErrDto : list){
            userIds.add(orderErrDto.getUserId());
        }
        //  获取用户信息
        if (userIds.size()>0){
            ResultDTO<Map<String, UserBaseInfoDTO>> result = userFeign.userInfos(userIds);
            if (result == null) throw new QuantizedException(QuantizedResultEnums.SERVER_IS_TOO_BUSY);
            if (result.getCode() != BaseConstant.REQUEST_SUCCESS) throw new RPCException(result);
            Map<String, UserBaseInfoDTO> userMap = result.getData();
            for (OrderErrDto orderErrDto : list){
                String userId = orderErrDto.getUserId();
                if (userMap.containsKey(userId)) orderErrDto.setUserBaseInfoDTO(userMap.get(userId));
            }
        }
        return list;
    }
}
