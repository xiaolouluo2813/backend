package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.constant.PushConstants;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.otc.common.enums.MarketApplyEnums;
import com.blockchain.server.otc.common.enums.MarketUserEnums;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.dto.marketapply.ListMarketApplyResultDTO;
import com.blockchain.server.otc.entity.MarketApply;
import com.blockchain.server.otc.entity.MarketUser;
import com.blockchain.server.otc.feign.PushFeign;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.MarketApplyMapper;
import com.blockchain.server.otc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MarketApplyServiceImpl implements MarketApplyService, ITxTransaction {

    @Autowired
    private MarketApplyMapper marketApplyMapper;
    @Autowired
    private MarketUserService marketUserService;
    @Autowired
    private MarketApplyHandleLogService applyHandleLogService;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private PushFeign pushFeign;

    @Override
    public List<ListMarketApplyResultDTO> list(String userName, String coinName, String status, String beginTime, String endTime) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listByUser(userName, coinName, status, beginTime, endTime);
        } else {
            //查询参数没有用户信息时
            return listByCondition(coinName, status, beginTime, endTime);
        }
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void agreeApply(String id, String sysUserId, String ipAddress) {
        //根据id查询申请记录
        MarketApply marketApply = checkMarketApplyNull(id);

        //更新记录
        marketApply.setStatus(MarketApplyEnums.STATUS_AGREE.getValue());
        marketApply.setModifyTime(new Date());
        marketApplyMapper.updateByPrimaryKeySelective(marketApply);

        //新增申请操作日志
        applyHandleLogService.insertApplyHandleLog(id, sysUserId, ipAddress,
                MarketApplyEnums.STATUS_NEW.getValue(), MarketApplyEnums.STATUS_AGREE.getValue());

        //申请市商
        if (marketApply.getApplyType().equals(MarketApplyEnums.TYPE_MARKET.getValue())) {
            //新增市商用户
            marketUserService.insertMarketUserByUserId(marketApply.getUserId(), marketApply.getAmount(), marketApply.getCoinName(), ipAddress, sysUserId);
            //发送手机通知
            pushToSingle(marketApply.getUserId(), PushEnums.OTC_MARKET_AGREE.getPushType());
        }
        //申请取消市商
        if (marketApply.getApplyType().equals(MarketApplyEnums.TYPE_CANCEL.getValue())) {
            //取消市商
            marketUserService.cancelMarketUserByUserId(marketApply.getUserId(), ipAddress, sysUserId);
            //发送手机通知
            pushToSingle(marketApply.getUserId(), PushEnums.OTC_MARKET_CANCEL_AGREE.getPushType());
        }


    }

    @Override
    @Transactional
    public void rejectApply(String id, String sysUserId, String ipAddress) {
        //根据id查询申请记录
        MarketApply marketApply = checkMarketApplyNull(id);
        String userId = marketApply.getUserId();

        //更新记录
        marketApply.setStatus(MarketApplyEnums.STATUS_REJECT.getValue());
        marketApply.setModifyTime(new Date());
        marketApplyMapper.updateByPrimaryKeySelective(marketApply);

        //新增申请操作日志
        applyHandleLogService.insertApplyHandleLog(id, sysUserId, ipAddress,
                MarketApplyEnums.STATUS_NEW.getValue(), MarketApplyEnums.STATUS_REJECT.getValue());

        //申请市商
        if (marketApply.getApplyType().equals(MarketApplyEnums.TYPE_MARKET.getValue())) {
            //发送手机通知
            pushToSingle(userId, PushEnums.OTC_MARKET_REJECT.getPushType());
        }
        //申请取消市商
        if (marketApply.getApplyType().equals(MarketApplyEnums.TYPE_CANCEL.getValue())) {
            //驳回时，将市商用户状态改回为市商认证
            MarketUser marketUser = marketUserService.getByUserId(userId);
            marketUser.setModifyTime(new Date());
            marketUser.setStatus(MarketUserEnums.STATUS_MARKET.getValue());
            marketUserService.updateByPrimaryKeySelective(marketUser);
            //发送手机通知
            pushToSingle(userId, PushEnums.OTC_MARKET_CANCEL_REJECT.getPushType());
        }
    }

    @Override
    public ListMarketApplyResultDTO getById(String id) {
        MarketApply marketApply = marketApplyMapper.selectByPrimaryKey(id);
        ListMarketApplyResultDTO resultDTO = new ListMarketApplyResultDTO();
        if (marketApply != null) {
            BeanUtils.copyProperties(marketApply, resultDTO);
            //查询用户信息
            UserBaseInfoDTO userBaseInfoDTO = selectUserByUserId(marketApply.getUserId());
            if (userBaseInfoDTO != null) {
                resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
                resultDTO.setRealName(userBaseInfoDTO.getRealName());
            }
        }
        return resultDTO;
    }

    /***
     * 判断市商申请记录是否为空
     * @param id
     * @return MarketApply
     */
    private MarketApply checkMarketApplyNull(String id) {
        MarketApply marketApply = marketApplyMapper.selectByPrimaryKey(id);
        if (marketApply == null) {
            throw new OtcException(OtcEnums.MARKET_APPLY_NULL);
        }
        return marketApply;
    }

    /***
     * 根据用户查询
     * @param userName
     * @param status
     * @return
     */
    private List<ListMarketApplyResultDTO> listByUser(String userName, String coinName, String status, String beginTime, String endTime) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return marketApplyMapper.list(null, coinName, status, beginTime, endTime);
        }
        //查询列表
        List<ListMarketApplyResultDTO> resultDTOS = marketApplyMapper.list(userBaseInfoDTO.getUserId(), coinName, status, beginTime, endTime);
        //设置用户信息
        for (ListMarketApplyResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * 根据条件查询
     * @param status
     * @return
     */
    private List<ListMarketApplyResultDTO> listByCondition(String coinName, String status, String beginTime, String endTime) {
        //查询列表
        List<ListMarketApplyResultDTO> resultDTOS = marketApplyMapper.list(null, coinName, status, beginTime, endTime);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMarketApplyResultDTO resultDTO : resultDTOS) {
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
        for (ListMarketApplyResultDTO resultDTO : resultDTOS) {
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

    /***
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    private UserBaseInfoDTO selectUserByUserId(String userId) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfo(userId);
        return resultDTO.getData();
    }

    /***
     * 发送手机消息通知（个推消息推送API）
     * @param userId
     * @param pushType
     */
    private void pushToSingle(String userId, String pushType) {
        Map<String, Object> payload = new HashMap<>();
        pushFeign.pushToSingle(userId, pushType, payload);
    }
}
