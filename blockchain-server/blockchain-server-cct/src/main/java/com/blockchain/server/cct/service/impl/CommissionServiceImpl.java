package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.dto.commission.ListCommissionResultDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.entity.Commission;
import com.blockchain.server.cct.feign.UserFeign;
import com.blockchain.server.cct.mapper.CommissionMapper;
import com.blockchain.server.cct.service.CoinService;
import com.blockchain.server.cct.service.CommissionService;
import com.blockchain.server.cct.service.WalletService;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CommissionServiceImpl implements CommissionService {

    private static Logger LOG = LoggerFactory.getLogger(CommissionServiceImpl.class);

    @Autowired
    private WalletService walletService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private CommissionMapper commissionMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListCommissionResultDTO> list(String userName, String puserName, String coinName, String status) {
        //账户不为空
        if (StringUtils.isNotBlank(userName)) {
            return listByUserName(userName, coinName, status);
        }
        //父级账户不为空
        if (StringUtils.isNotBlank(puserName)) {
            return listByPUserName(puserName, coinName, status);
        }
        //账户和父级都不为空
        if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(puserName)) {
            return listByUserAndPuser(userName, puserName, coinName, status);
        }
        //查询其他
        return listByCondition(coinName, status);
    }

    @Override
    public List<String> listByStatus(String status, Integer pageNum, Integer pageSize) {
        return commissionMapper.listByStatus(status, pageNum, pageSize);
    }

    @Override
    public Commission selectByIdForUpdate(String id) {
        return commissionMapper.selectByIdForUpdate(id);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public int issueCommission(String id) {
        //排他锁查询
        Commission commission = selectByIdForUpdate(id);
        //佣金未发放状态时
        if (commission.getStatus().equals(CCTConstant.STATUS_NO)) {
            //根据二级货币查询主网标识
            Coin coin = coinService.selectByUnitAndGroupByUnit(commission.getCoinName());
            //增加资金
            walletService.handleRealBalance(commission.getPid(), commission.getId(), commission.getCoinName(),
                    coin.getUnitNet(), commission.getAmount(), BigDecimal.ZERO, BigDecimal.ZERO);
            //更新佣金为已发放
            commission.setStatus(CCTConstant.STATUS_YES);
            commission.setModifyTime(new Date());
            return commissionMapper.updateByPrimaryKeySelective(commission);
        }
        return 0;
    }

    /***
     * 根据账户查询
     * @param userName
     * @param coinName
     * @param status
     * @return
     */
    private List<ListCommissionResultDTO> listByUserName(String userName, String coinName, String status) {
        //调用feign查询用户信息
        UserBaseInfoDTO user = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (user == null) {
            return commissionMapper.list(null, null, coinName, status);
        }
        //查询列表
        List<ListCommissionResultDTO> resultDTOS = commissionMapper.list(user.getUserId(), null, coinName, status);
        //设置用户信息
        for (ListCommissionResultDTO resultDTO : resultDTOS) {
            //调用feign查询用户信息
            resultDTO.setUserName(user.getMobilePhone());
            resultDTO.setRealName(user.getRealName());
            UserBaseInfoDTO puser = selectUserByUserId(resultDTO.getPid());
            if (puser != null) {
                resultDTO.setPuserName(puser.getMobilePhone());
                resultDTO.setPrealName(puser.getRealName());
            }
        }
        return resultDTOS;
    }

    /***
     * 根据父级账户查询
     * @param puserName
     * @param coinName
     * @param status
     * @return
     */
    private List<ListCommissionResultDTO> listByPUserName(String puserName, String coinName, String status) {
        //调用feign查询父级账户
        UserBaseInfoDTO puser = selectUserByUserName(puserName);
        //用户信息等于空，返回没有userid的查询数据
        if (puser == null) {
            return commissionMapper.list(null, null, coinName, status);
        }
        //查询列表
        List<ListCommissionResultDTO> resultDTOS = commissionMapper.list(null, puser.getUserId(), coinName, status);
        //设置用户信息
        for (ListCommissionResultDTO resultDTO : resultDTOS) {
            resultDTO.setPuserName(puser.getMobilePhone());
            resultDTO.setPrealName(puser.getRealName());
            UserBaseInfoDTO user = selectUserByUserId(resultDTO.getUserId());
            if (puser != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
        }
        return resultDTOS;
    }

    /***
     * 根据账户和父级账户查询
     * @param userName
     * @param puserName
     * @param coinName
     * @param status
     * @return
     */
    private List<ListCommissionResultDTO> listByUserAndPuser(String userName, String puserName, String coinName, String status) {
        //父级账户
        UserBaseInfoDTO puser = selectUserByUserName(puserName);
        UserBaseInfoDTO user = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (puser != null && user != null) {
            //查询列表
            List<ListCommissionResultDTO> resultDTOS = commissionMapper.list(user.getUserId(), puser.getUserId(), coinName, status);
            //设置用户信息
            for (ListCommissionResultDTO resultDTO : resultDTOS) {
                resultDTO.setPuserName(puser.getMobilePhone());
                resultDTO.setPrealName(puser.getRealName());
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
            return resultDTOS;
        }

        return commissionMapper.list(null, null, coinName, status);
    }

    /***
     * 根据条件查询
     * @param coinName
     * @param status
     * @return
     */
    private List<ListCommissionResultDTO> listByCondition(String coinName, String status) {
        //查询列表
        List<ListCommissionResultDTO> resultDTOS = commissionMapper.list(null, null, coinName, status);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListCommissionResultDTO resultDTO : resultDTOS) {
            userIds.add(resultDTO.getUserId());
            userIds.add(resultDTO.getPid());
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
        for (ListCommissionResultDTO resultDTO : resultDTOS) {
            //根据用户id从map中获取用户数据
            UserBaseInfoDTO user = userInfos.get(resultDTO.getUserId());
            UserBaseInfoDTO puser = userInfos.get(resultDTO.getPid());
            //防空
            if (user != null) {
                resultDTO.setUserName(user.getMobilePhone());
                resultDTO.setRealName(user.getRealName());
            }
            if (puser != null) {
                resultDTO.setPuserName(puser.getMobilePhone());
                resultDTO.setPrealName(puser.getRealName());
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
}
