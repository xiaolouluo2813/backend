package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.dto.userhandlelog.ListUserHandleLogResultDTO;
import com.blockchain.server.otc.dto.userpayinfo.ListUserPayInfoResultDTO;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.UserPayInfoMapper;
import com.blockchain.server.otc.service.UserPayInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserPayInfoServiceImpl implements UserPayInfoService {

    @Autowired
    private UserPayInfoMapper userPayInfoMapper;
    @Autowired
    private UserFeign userFeign;


    @Override
    public List<ListUserPayInfoResultDTO> listUserPayInfo(String userName, String payType) {
        if (StringUtils.isNotBlank(userName)) {
            return listUserPayInfoByUser(userName, payType);
        } else {
            return listUserPayInfo(payType);
        }
    }

    /***
     * 根据用户查询用户支付信息列表
     * @param userName
     * @param payType
     * @return
     */
    private List<ListUserPayInfoResultDTO> listUserPayInfoByUser(String userName, String payType) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return userPayInfoMapper.listUserPayInfo("", payType);
        }
        //查询列表
        List<ListUserPayInfoResultDTO> resultDTOS = userPayInfoMapper.listUserPayInfo(userBaseInfoDTO.getUserId(), payType);
        //设置用户信息
        for (ListUserPayInfoResultDTO resultDTO : resultDTOS) {
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
        }
        return resultDTOS;
    }

    /***
     * 查询用户支付信息列表
     * @param payType
     * @return
     */
    private List<ListUserPayInfoResultDTO> listUserPayInfo(String payType) {
        //查询列表
        List<ListUserPayInfoResultDTO> resultDTOS = userPayInfoMapper.listUserPayInfo("", payType);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListUserPayInfoResultDTO resultDTO : resultDTOS) {
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
        for (ListUserPayInfoResultDTO resultDTO : resultDTOS) {
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
