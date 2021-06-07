package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.dto.bill.ListBillResultDTO;
import com.blockchain.server.otc.dto.dealstats.ListDealStatsResultDTO;
import com.blockchain.server.otc.entity.Bill;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.BillMapper;
import com.blockchain.server.otc.service.BillService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListBillResultDTO> listBill(String userName, String beginTime, String endTime) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listBillByUserName(userName, beginTime, endTime);
        } else {
            //查询参数没有用户信息时
            return listBill(beginTime, endTime);
        }
    }

    @Override
    @Transactional
    public int insertBill(String userId, String recordNumber, BigDecimal freeBalance, BigDecimal freezeBalance, String billType, String coinName) {
        Bill bill = new Bill();
        bill.setId(UUID.randomUUID().toString());
        bill.setUserId(userId);
        bill.setRecordNumber(recordNumber);
        bill.setBillType(billType);
        bill.setFreeBalance(freeBalance);
        bill.setFreezeBalance(freezeBalance);
        bill.setCoinName(coinName);
        bill.setCreateTime(new Date());
        return billMapper.insertSelective(bill);
    }

    /***
     * 根据账户查询用户资金对账记录
     * @param userName
     * @return
     */
    private List<ListBillResultDTO> listBillByUserName(String userName, String beginTime, String endTime) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return billMapper.listBill(null, beginTime, endTime);
        }
        //查询列表
        List<ListBillResultDTO> resultDTOS = billMapper.listBill(userBaseInfoDTO.getUserId(), beginTime, endTime);
        //设置用户信息
        for (ListBillResultDTO resultDTO : resultDTOS) {
            resultDTO.setUserName(userBaseInfoDTO.getMobilePhone());
            resultDTO.setRealName(userBaseInfoDTO.getRealName());
        }
        return resultDTOS;
    }

    /***
     * 查询用户资金对账记录
     * @return
     */
    private List<ListBillResultDTO> listBill(String beginTime, String endTime) {
        //查询列表
        List<ListBillResultDTO> resultDTOS = billMapper.listBill(null, beginTime, endTime);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListBillResultDTO resultDTO : resultDTOS) {
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
        for (ListBillResultDTO resultDTO : resultDTOS) {
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
