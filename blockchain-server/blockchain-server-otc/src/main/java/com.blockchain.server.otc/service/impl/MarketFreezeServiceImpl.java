package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.dto.marketfreeze.ListMarketFreezeResultDTO;
import com.blockchain.server.otc.entity.MarketFreeze;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.MarketFreezeMapper;
import com.blockchain.server.otc.service.MarketFreezeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MarketFreezeServiceImpl implements MarketFreezeService {

    @Autowired
    private MarketFreezeMapper marketFreezeMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListMarketFreezeResultDTO> list(String userName) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listByUser(userName);
        } else {
            //查询参数没有用户信息时
            return listByCondition();
        }
    }

    @Override
    @Transactional
    public int insertMarketFreeze(String userId, String applyId, BigDecimal amount, String coin) {
        MarketFreeze marketFreeze = new MarketFreeze();
        Date now = new Date();
        marketFreeze.setId(UUID.randomUUID().toString());
        marketFreeze.setMarketApplyId(null);
        marketFreeze.setUserId(userId);
        marketFreeze.setAmount(amount);
        marketFreeze.setCoinName(coin);
        marketFreeze.setCreateTime(now);
        return marketFreezeMapper.insertSelective(marketFreeze);
    }

    @Override
    @Transactional
    public int deleteMarketFreeze(String userId) {
        return marketFreezeMapper.deleteByUserId(userId);
    }

    @Override
    public MarketFreeze getByUserId(String userId) {
        return marketFreezeMapper.selectByUserId(userId);
    }

    /***
     * 根据用户查询
     * @param userName
     * @return
     */
    private List<ListMarketFreezeResultDTO> listByUser(String userName) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return marketFreezeMapper.list(null);
        }
        //查询列表
        List<ListMarketFreezeResultDTO> resultDTOS = marketFreezeMapper.list(userBaseInfoDTO.getUserId());
        //设置用户信息
        for (ListMarketFreezeResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * 根据条件查询
     * @return
     */
    private List<ListMarketFreezeResultDTO> listByCondition() {
        //查询列表
        List<ListMarketFreezeResultDTO> resultDTOS = marketFreezeMapper.list(null);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMarketFreezeResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getUserId());
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
        for (ListMarketFreezeResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取用户数据
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            //防空
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
        }
        //返回列表
        return resultDTOS;
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
     * 根据id集合查询多个用户信息
     * @param userIds
     * @return
     */
    private Map<String, UserBaseInfoDTO> listUserInfos(Set<String> userIds) {
        ResultDTO<Map<String, UserBaseInfoDTO>> resultDTO = userFeign.listUserInfo(userIds);
        return resultDTO.getData();
    }
}
