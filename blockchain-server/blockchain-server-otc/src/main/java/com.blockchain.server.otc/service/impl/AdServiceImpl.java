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
        //??????????????????????????????
        if (StringUtils.isNotBlank(userName)) {
            return listAdByUser(adNumber, userName, coinName, unitName, adType, adStatus, beginTime, endTime);
        } else {
            //?????????????????????????????????
            return listAdByCondition(adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        }
    }

    /***
     * ????????????????????????
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
        //??????feign??????????????????
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //????????????????????????????????????userid???????????????
        if (userBaseInfoDTO == null) {
            return adMapper.listAd(null, adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        }
        //????????????
        List<ListAdResultDTO> resultDTOS = adMapper.listAd(userBaseInfoDTO.getUserId(), adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        //??????????????????
        for (ListAdResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * ????????????
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
        //????????????
        List<ListAdResultDTO> resultDTOS = adMapper.listAd(null, adNumber, coinName, unitName, adType, adStatus, beginTime, endTime);
        //??????userId??????????????????????????????????????????
        Set userIds = new HashSet();
        //????????????id
        for (ListAdResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getUserId());
        }
        //????????????ids??????
        if (userIds.size() == 0) {
            return resultDTOS;
        }
        //??????feign???????????????????????????
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //??????????????????????????????
        if (userInfos.size() == 0) {
            return resultDTOS;
        }
        //??????????????????
        for (ListAdResultDTO resultDTO : resultDTOS) {
            //????????????id???map?????????????????????
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            //??????
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
        }
        //????????????
        return resultDTOS;
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void cancelAd(String sysUserId, String ipAddress, String adId) {
        //?????????????????????
        Ad ad = selectByIdForUpdate(adId);
        //????????????????????????????????????
        if (!ad.getAdStatus().equals(AdEnums.STATUS_DEFAULT.getValue())) {
            throw new OtcException(OtcEnums.AD_CANCEL_STATUS_NOT_DEFAULT);
        }
        //?????????????????????????????????????????????
        boolean flag = orderService.checkOrdersUnfinished(adId);
        //true????????????????????????????????????
        if (flag) {
            throw new OtcException(OtcEnums.CANCEL_AD_ORDERS_UNFINISHED);
        }

        //??????????????????
        if (ad.getAdType().equals(AdEnums.TYPE_SELL.getValue())) {
            //????????????????????????
            if (ad.getLastNum().compareTo(BigDecimal.ZERO) > 0) {
                //?????????
                BigDecimal serviceCharge = ad.getLastNum().multiply(ad.getChargeRatio());
                //????????????
                BigDecimal freeBalance = ad.getLastNum().add(serviceCharge);
                //????????????
                BigDecimal freezeBalance = freeBalance.multiply(new BigDecimal("-1"));
                //????????????
                walletService.handleBalance(ad.getUserId(), ad.getAdNumber(), ad.getCoinName(), ad.getUnitName(), freeBalance, freezeBalance);
                //??????????????????
                billService.insertBill(ad.getUserId(), ad.getAdNumber(), freeBalance, freezeBalance, BillEnums.TYPE_CANCEL.getValue(), ad.getCoinName());
            }
        }

        //????????????????????????
        InsertAdHandleLogParamDTO adHandleLogDTO = new InsertAdHandleLogParamDTO();
        adHandleLogDTO.setAdNumber(ad.getAdNumber());
        adHandleLogDTO.setBeforeStatus(ad.getAdStatus());
        adHandleLogDTO.setAfterStatus(AdEnums.STATUS_CANCEL.getValue());
        adHandleLogDTO.setSysUserId(sysUserId);
        adHandleLogDTO.setIpAddress(ipAddress);
        adHandleLogService.insertAdHandleLog(adHandleLogDTO);

        //??????????????????
        ad.setAdStatus(AdEnums.STATUS_CANCEL.getValue());
        ad.setModifyTime(new Date());
        adMapper.updateByPrimaryKeySelective(ad);

        //?????????????????????????????????
        pushToSingle(ad.getUserId(), ad.getId(), PushEnums.OTC_AD_ADMIND_CANCEL.getPushType());
    }

    /***
     * ??????userName??????????????????
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserByUserName(String userName) {
        ResultDTO<UserBaseInfoDTO> resultDTO = userFeign.selectUserInfoByUserName(userName);
        return resultDTO.getData();
    }

    /***
     * ??????id??????????????????????????????
     * @param userIds
     * @return
     */
    private Map<String, UserBaseInfoDTO> listUserInfos(Set<String> userIds) {
        ResultDTO<Map<String, UserBaseInfoDTO>> resultDTO = userFeign.listUserInfo(userIds);
        return resultDTO.getData();
    }

    /***
     * ?????????????????????????????????????????????API???
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
