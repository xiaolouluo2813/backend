package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.constant.PushConstants;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.enums.PushEnums;
import com.blockchain.server.otc.common.enums.AdEnums;
import com.blockchain.server.otc.common.enums.BillEnums;
import com.blockchain.server.otc.common.enums.OrderEnums;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.dto.ad.ListAdResultDTO;
import com.blockchain.server.otc.dto.adhandlelog.InsertAdHandleLogParamDTO;
import com.blockchain.server.otc.entity.Ad;
import com.blockchain.server.otc.entity.Order;
import com.blockchain.server.otc.feign.PushFeign;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.AdMapper;
import com.blockchain.server.otc.service.*;
import com.codingapi.tx.annotation.ITxTransaction;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AdServiceImpl implements AdService, ITxTransaction {

    @Autowired
    private AdMapper adMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private BillService billService;
    @Autowired
    private AdHandleLogService adHandleLogService;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private PushFeign pushFeign;

    @Override
    public Ad selectById(String adId) {
        return adMapper.selectByPrimaryKey(adId);
    }

    @Override
    public Ad selectByIdForUpdate(String adId) {
        return adMapper.selectByIdForUpdate(adId);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(Ad ad) {
        return adMapper.updateByPrimaryKeySelective(ad);
    }

    @Override
    public List<ListAdResultDTO> listAd(String adNumber, String userName, String coinName,
                                        String unitName, String adType, String adStatus,
                                        String beginTime, String endTime) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listAdByUser(adNumber, userName, coinName, unitName, adType, adStatus, beginTime, endTime);
        } else {
            //查询参数没有用户信息时
            return listAdByCondition(adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        }
    }

    /***
     * 根据用户查询广告
     * @param adNumber
     * @param userName
     * @param coinName
     * @param unitName
     * @param adType
     * @param adStatus
     * @return
     */
    private List<ListAdResultDTO> listAdByUser(String adNumber, String userName, String coinName,
                                               String unitName, String adType, String adStatus,
                                               String beginTime, String endTime) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return adMapper.listAd(null, adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        }
        //查询列表
        List<ListAdResultDTO> resultDTOS = adMapper.listAd(userBaseInfoDTO.getUserId(), adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        //设置用户信息
        for (ListAdResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * 查询广告
     * @param adNumber
     * @param coinName
     * @param unitName
     * @param adType
     * @param adStatus
     * @return
     */
    private List<ListAdResultDTO> listAdByCondition(String adNumber, String coinName, String unitName,
                                                    String adType, String adStatus,
                                                    String beginTime, String endTime) {
        //查询列表
        List<ListAdResultDTO> resultDTOS = adMapper.listAd(null, adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListAdResultDTO resultDTO : resultDTOS) {
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
        for (ListAdResultDTO resultDTO : resultDTOS) {
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

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void cancelAd(String sysUserId, String ipAddress, String adId) {
        //查询广告排他锁
        Ad ad = selectByIdForUpdate(adId);
        //判断广告状态是否可以撤单
        if (!ad.getAdStatus().equals(AdEnums.STATUS_DEFAULT.getValue())) {
            throw new OtcException(OtcEnums.AD_CANCEL_STATUS_NOT_DEFAULT);
        }
        //查询广告伞下是否还有未完成订单
        boolean flag = orderService.checkOrdersUnfinished(adId);
        //true存在未完成订单，抛出异常
        if (flag) {
            throw new OtcException(OtcEnums.CANCEL_AD_ORDERS_UNFINISHED);
        }

        //卖单解冻余额
        if (ad.getAdType().equals(AdEnums.TYPE_SELL.getValue())) {
            //广告还有剩余数量
            if (ad.getLastNum().compareTo(BigDecimal.ZERO) > 0) {
                //手续费
                BigDecimal serviceCharge = ad.getLastNum().multiply(ad.getChargeRatio());
                //增加余额
                BigDecimal freeBalance = ad.getLastNum().add(serviceCharge);
                //解冻余额
                BigDecimal freezeBalance = freeBalance.multiply(new BigDecimal("-1"));
                //解冻余额
                walletService.handleBalance(ad.getUserId(), ad.getAdNumber(), ad.getCoinName(), ad.getUnitName(), freeBalance, freezeBalance);
                //记录资金变动
                billService.insertBill(ad.getUserId(), ad.getAdNumber(), freeBalance, freezeBalance, BillEnums.TYPE_CANCEL.getValue(), ad.getCoinName());
            }
        }

        //新增广告操作日志
        InsertAdHandleLogParamDTO adHandleLogDTO = new InsertAdHandleLogParamDTO();
        adHandleLogDTO.setAdNumber(ad.getAdNumber());
        adHandleLogDTO.setBeforeStatus(ad.getAdStatus());
        adHandleLogDTO.setAfterStatus(AdEnums.STATUS_CANCEL.getValue());
        adHandleLogDTO.setSysUserId(sysUserId);
        adHandleLogDTO.setIpAddress(ipAddress);
        adHandleLogService.insertAdHandleLog(adHandleLogDTO);

        //更新广告信息
        ad.setAdStatus(AdEnums.STATUS_CANCEL.getValue());
        ad.setModifyTime(new Date());
        adMapper.updateByPrimaryKeySelective(ad);

        //撤单后发送消息通知用户
        pushToSingle(ad.getUserId(), ad.getId(), PushEnums.OTC_AD_ADMIND_CANCEL.getPushType());
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
     * 发送手机消息通知（个推消息推送API）
     * @param userId
     * @param orderId
     * @param pushType
     */
    private void pushToSingle(String userId, String orderId, String pushType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PushConstants.AD_ID, orderId);
        pushFeign.pushToSingle(userId, pushType, payload);
    }
}
