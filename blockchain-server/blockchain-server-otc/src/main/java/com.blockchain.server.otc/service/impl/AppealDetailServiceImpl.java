package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.dto.appealdetail.ListAppealDetailResultDTO;
import com.blockchain.server.otc.dto.appealimg.ListAppealImgResultDTO;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.AppealDetailMapper;
import com.blockchain.server.otc.service.AppealDetailService;
import com.blockchain.server.otc.service.AppealImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AppealDetailServiceImpl implements AppealDetailService {

    @Autowired
    private AppealDetailMapper appealDetailMapper;
    @Autowired
    private AppealImgService appealImgService;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListAppealDetailResultDTO> listAppealDetailByAppealId(String appealId) {
        List<ListAppealDetailResultDTO> resultDTOS = appealDetailMapper.listAppealDetailByAppealId(appealId);
        //防空
        if (resultDTOS.size() == 0) {
            return resultDTOS;
        }
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListAppealDetailResultDTO resultDTO : resultDTOS) {
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
        for (ListAppealDetailResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取用户数据
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            //防空
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
            //查询申诉详情对应的申诉图片列表
            List<ListAppealImgResultDTO> appealImgs = appealImgService.listAppealImgByAppealDetailId(resultDTO.getId());
            resultDTO.setAppealImgs(appealImgs);
        }

        //返回数据
        return resultDTOS;
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
