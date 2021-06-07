package com.blockchain.server.otc.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.otc.common.enums.BillEnums;
import com.blockchain.server.otc.common.enums.MarketUserEnums;
import com.blockchain.server.otc.common.enums.OtcEnums;
import com.blockchain.server.otc.common.exception.OtcException;
import com.blockchain.server.otc.dto.marketuser.ListMarketUserResultDTO;
import com.blockchain.server.otc.entity.MarketFreeze;
import com.blockchain.server.otc.entity.MarketUser;
import com.blockchain.server.otc.feign.UserFeign;
import com.blockchain.server.otc.mapper.MarketUserMapper;
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
public class MarketUserServiceImpl implements MarketUserService, ITxTransaction {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private MarketUserMapper marketUserMapper;
    @Autowired
    private WalletService walletService;
    @Autowired
    private MarketUserHandleLogService userHandleLogService;
    @Autowired
    private MarketFreezeService marketFreezeService;
    @Autowired
    private BillService billService;
    @Autowired
    private ConfigService configService;

    private static final String UNIT_NAME = "CNY"; //默认二级货币
    private static final BigDecimal MINUS_ONE = new BigDecimal("-1"); //大数据类型的-1

    @Override
    public List<ListMarketUserResultDTO> list(String userName, String status) {
        //查询参数有用户信息时
        if (StringUtils.isNotBlank(userName)) {
            return listByUser(userName, status);
        } else {
            //查询参数没有用户信息时
            return listByCondition(status);
        }
    }

    @Override
    public MarketUser getByUserId(String userId) {
        return marketUserMapper.selectByUser(userId);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void insertMarketUserByUserName(String userName, BigDecimal amount, String coin, String ipAddress, String sysUserId) {
        //查询userId
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //判断用户是否存在
        if (userBaseInfoDTO == null) {
            throw new OtcException(OtcEnums.USER_NULL);
        }

        //判断市商用户是否存在
        MarketUser marketUser = checkMarketUserExist(userBaseInfoDTO.getUserId());

        if (marketUser != null) {
            //恢复市商用户
            this.becomeMarketUser(marketUser.getId(), amount, coin, ipAddress, sysUserId);
        } else {
            //新增市商用户
            newMarketUser(userBaseInfoDTO.getUserId(), amount, coin, ipAddress, sysUserId);
        }
    }

    @Override
    @Transactional
    public void insertMarketUserByUserId(String userId, BigDecimal amount, String coin, String ipAddress, String sysUserId) {
        //判断市商用户是否存在
        MarketUser marketUser = checkMarketUserExist(userId);

        if (marketUser != null) {
            //恢复市商用户
            this.becomeMarketUser(marketUser.getId(), amount, coin, ipAddress, sysUserId);
        } else {
            //新增市商用户
            newMarketUser(userId, amount, coin, ipAddress, sysUserId);
        }
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void cancelMarketUserById(String id, String ipAddress, String sysUserId) {
        //查询市商用户
        MarketUser marketUser = marketUserMapper.selectByIdForUpdate(id);
        //判断市商用户是否存在
        if (marketUser == null) {
            throw new OtcException(OtcEnums.MARKET_USER_NULL);
        }

        //取消市商
        cancelMarketUser(marketUser, ipAddress, sysUserId);
    }

    @Override
    @Transactional
    public void cancelMarketUserByUserId(String userId, String ipAddress, String sysUserId) {
        //查询市商用户
        MarketUser marketUser = marketUserMapper.selectByUser(userId);
        //判断市商用户是否存在
        if (marketUser == null) {
            throw new OtcException(OtcEnums.MARKET_USER_NULL);
        }

        //取消市商
        cancelMarketUser(marketUser, ipAddress, sysUserId);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void becomeMarketUser(String id, BigDecimal amount, String coin, String ipAddress, String sysUserId) {
        //查询市商用户
        MarketUser marketUser = marketUserMapper.selectByIdForUpdate(id);
        //判断市商用户是否存在
        if (marketUser == null) {
            throw new OtcException(OtcEnums.MARKET_USER_NULL);
        }

        //市商用户id
        String userId = marketUser.getUserId();

        //改变状态为市商
        marketUser.setStatus(MarketUserEnums.STATUS_MARKET.getValue());
        marketUser.setModifyTime(new Date());
        marketUserMapper.updateByPrimaryKeySelective(marketUser);

        //检查保证金的数量
        BigDecimal freezeAmount = checkMakretFreezeAmount(amount);
        BigDecimal freeAmount = freezeAmount.multiply(MINUS_ONE);
        //检查保证金的币种
        String freezeCoin = checkMakretFreezeCoin(coin);

        //新增押金记录
        marketFreezeService.insertMarketFreeze(userId, null, freezeAmount, freezeCoin);

        //新增管理员操作
        String handleLogId = userHandleLogService.insertUserHandleLog(ipAddress, sysUserId, userId, MarketUserEnums.STATUS_NOTMARKET.getValue(),
                MarketUserEnums.STATUS_MARKET.getValue());

        //扣款
        walletService.handleBalance(userId, handleLogId, freezeCoin, UNIT_NAME, freeAmount, freezeAmount);
        //记录用户资金变动
        billService.insertBill(userId, handleLogId, freeAmount, freezeAmount, BillEnums.MARKET_USER.getValue(), freezeCoin);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(MarketUser marketUser) {
        return marketUserMapper.updateByPrimaryKeySelective(marketUser);
    }

    /***
     * 新增市商用户
     * @param userId
     * @param amount
     * @param coin
     * @param ipAddress
     * @param sysUserId
     */
    private void newMarketUser(String userId, BigDecimal amount, String coin, String ipAddress, String sysUserId) {
        //检查保证金的数量
        BigDecimal freezeAmount = checkMakretFreezeAmount(amount);
        BigDecimal freeAmount = freezeAmount.multiply(MINUS_ONE);
        //检查保证金的币种
        String freezeCoin = checkMakretFreezeCoin(coin);

        //新增市商用户
        MarketUser marketUser = new MarketUser();
        Date now = new Date();
        marketUser.setId(UUID.randomUUID().toString());
        marketUser.setUserId(userId);
        marketUser.setCreateTime(now);
        marketUser.setModifyTime(now);
        marketUser.setStatus(MarketUserEnums.STATUS_MARKET.getValue());
        marketUserMapper.insertSelective(marketUser);

        //新增押金记录
        marketFreezeService.insertMarketFreeze(userId, null, freezeAmount, freezeCoin);

        //新增管理员操作
        String handleLogId = userHandleLogService.insertUserHandleLog(ipAddress, sysUserId, userId, null, MarketUserEnums.STATUS_MARKET.getValue());

        //扣款
        walletService.handleBalance(userId, handleLogId, freezeCoin, UNIT_NAME, freeAmount, freezeAmount);
        //记录用户资金变动
        billService.insertBill(userId, handleLogId, freeAmount, freezeAmount, BillEnums.MARKET_USER.getValue(), freezeCoin);
    }

    /***
     * 取消市商
     * @param marketUser
     * @param ipAddress
     * @param sysUserId
     */
    private void cancelMarketUser(MarketUser marketUser, String ipAddress, String sysUserId) {
        //市商用户id
        String userId = marketUser.getUserId();

        //禁用市商用户
        marketUser.setStatus(MarketUserEnums.STATUS_NOTMARKET.getValue());
        marketUser.setModifyTime(new Date());
        marketUserMapper.updateByPrimaryKeySelective(marketUser);

        //判断保证金记录是否存在
        MarketFreeze marketFreeze = checkMarketFreezeNull(userId);

        //解冻资金
        BigDecimal freeAmount = marketFreeze.getAmount();
        BigDecimal freezeAmount = freeAmount.multiply(MINUS_ONE);

        //解冻的币种
        String coin = marketFreeze.getCoinName();

        //删除保证金记录
        marketFreezeService.deleteMarketFreeze(userId);

        //新增管理员操作
        String handleLogId = userHandleLogService.insertUserHandleLog(ipAddress, sysUserId, userId, MarketUserEnums.STATUS_MARKET.getValue(),
                MarketUserEnums.STATUS_NOTMARKET.getValue());

        //退款
        walletService.handleBalance(userId, handleLogId, coin, UNIT_NAME, freeAmount, freezeAmount);
        //记录用户资金变动
        billService.insertBill(userId, handleLogId, freeAmount, freezeAmount, BillEnums.CANCEL_MARKET_USER.getValue(), coin);
    }

    /***
     * 判断市商用户是否存在
     * @param userId
     */
    private MarketUser checkMarketUserExist(String userId) {
        MarketUser marketUser = marketUserMapper.selectByUser(userId);
        if (marketUser != null) {
            return marketUser;
        }
        return null;
    }

    /***
     * 判断保证金是否为空
     * @param userId
     */
    private MarketFreeze checkMarketFreezeNull(String userId) {
        MarketFreeze marketFreeze = marketFreezeService.getByUserId(userId);
        if (marketFreeze == null) {
            throw new OtcException(OtcEnums.MARKET_FREEZE_NULL);
        }
        return marketFreeze;
    }

    /***
     * 判断是否输入的保证金数量
     * 如果没输入，则查询配置表中的保证金数量字段
     * @param amount
     */
    private BigDecimal checkMakretFreezeAmount(BigDecimal amount) {
        //数量等于空时，查询保证金数量配置
        if (amount == null) {
            return configService.selectMarketFreezeAmount();
        } else {
            //保证金数量小于0，抛出异常
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                throw new OtcException(OtcEnums.MARKET_FREEZE_LESS_THAN_ZERO);
            }
            return amount;
        }
    }

    private String checkMakretFreezeCoin(String coin) {
        //输入的代币为空
        if (StringUtils.isBlank(coin)) {
            //查询保证金代币配置
            String freezeCoin = configService.selectMarketFreezeCoin();
            //保证金代币配置为空，抛出异常
            if (StringUtils.isBlank(freezeCoin)) {
                throw new OtcException(OtcEnums.MARKET_FREEZE_COIN_CONFIG_NULL);
            }
            return freezeCoin;
        }
        //不为空时，返回输入的代币
        return coin;
    }

    /***
     * 根据用户查询
     * @param userName
     * @param status
     * @return
     */
    private List<ListMarketUserResultDTO> listByUser(String userName, String status) {
        //调用feign查询用户信息
        UserBaseInfoDTO userBaseInfoDTO = selectUserByUserName(userName);
        //用户信息等于空，返回没有userid的查询数据
        if (userBaseInfoDTO == null) {
            return marketUserMapper.list(null, status);
        }
        //查询列表
        List<ListMarketUserResultDTO> resultDTOS = marketUserMapper.list(userBaseInfoDTO.getUserId(), status);
        //设置用户信息
        for (ListMarketUserResultDTO resultDTO : resultDTOS) {
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
    private List<ListMarketUserResultDTO> listByCondition(String status) {
        //查询列表
        List<ListMarketUserResultDTO> resultDTOS = marketUserMapper.list(null, status);
        //封装userId集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMarketUserResultDTO resultDTO : resultDTOS) {
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
        for (ListMarketUserResultDTO resultDTO : resultDTOS) {
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
