package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.dto.userhandlelog.ListUserHandleLogResultDTO;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.UserHandleLogMapper;
import com.blockchain.server.otc.service.UserHandleLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserHandleLogServiceImpl implements UserHandleLogService {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private UserHandleLogMapper userHandleLogMapper;

    @Override
    public List<ListUserHandleLogResultDTO> listUserHandleLog(String userName, String handleNumber,
                                                              String handleNumberType, String handleType,
                                                              String beginTime, String endTime) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listUserHandleLogByUser(userName, handleNumber, handleNumberType, handleType, beginTime, endTime);
        } else {
            //查询参数没有用户信息时
            return listUserHandleLogByCondition(handleNumber, handleNumberType, handleType, beginTime, endTime);
        }
    }

    /***
     * 根据账户查询用户操作日志
     * @param userName
     * @param handleNumber
     * @param handleNumberType
     * @param handleType
     * @return
     */
    private List<ListUserHandleLogResultDTO> listUserHandleLogByUser(String userName, String handleNumber,
                                                                     String handleNumberType, String handleType,
                                                                     String beginTime, String endTime) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return userHandleLogMapper.listUserHandleLog("", handleNumber, handleNumberType, handleType, beginTime, endTime);
        }
        //查询列表
        List<ListUserHandleLogResultDTO> resultDTOS = userHandleLogMapper.listUserHandleLog(userBaseInfoDTO.getUserId(), handleNumber, handleNumberType, handleType, beginTime, endTime);
        //设置用户信息
        for (ListUserHandleLogResultDTO resultDTO : resultDTOS) {
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
        }
        return resultDTOS;
    }

    /***
     * 根据参数查询用户操作日志
     * @param handleNumber
     * @param handleNumberType
     * @param handleType
     * @param beginTime
     * @param endTime
     * @return
     */
    private List<ListUserHandleLogResultDTO> listUserHandleLogByCondition(String handleNumber, String handleNumberType, String handleType,
                                                                          String beginTime, String endTime) {
        //查询列表
        List<ListUserHandleLogResultDTO> resultDTOS = userHandleLogMapper.listUserHandleLog("", handleNumber, handleNumberType, handleType, beginTime, endTime);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListUserHandleLogResultDTO resultDTO : resultDTOS) {
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
        for (ListUserHandleLogResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取用户数据
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            //防空
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
        }

        return resultDTOS;
    }

    /***
     * 根据userName查询用户信息
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserByUserName(String userName) {
        ResultDTO resultDTO = userFeign.selectUserInfoByUserName(userName);
        return (UserBaseInfoDTO) resultDTO.getData();
    }

    /***
     * 根据id集合查询多个用户信息
     * @param userIds
     * @return
     */
    private Map<String, UserBaseInfoDTO> listUserInfos(Set<String> userIds) {
        ResultDTO resultDTO = userFeign.listUserInfo(userIds);
        return (Map<String, UserBaseInfoDTO>) resultDTO.getData();
    }
}
