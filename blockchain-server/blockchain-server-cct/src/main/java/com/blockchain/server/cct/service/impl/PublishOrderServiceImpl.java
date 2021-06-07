package com.blockchain.server.cct.service.impl;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.MarketDTO;
import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.common.constant.QuantizedConstant;
import com.blockchain.server.cct.common.constant.RedisConstant;
import com.blockchain.server.cct.common.enums.CCTEnums;
import com.blockchain.server.cct.common.exception.CCTException;
import com.blockchain.server.cct.common.redisson.RedissonTool;
import com.blockchain.server.cct.dto.publishOrder.ListOrderParamDTO;
import com.blockchain.server.cct.dto.publishOrder.ListOrderResultDTO;
import com.blockchain.server.cct.entity.Coin;
import com.blockchain.server.cct.entity.PublishOrder;
import com.blockchain.server.cct.entity.PublishOrderLog;
import com.blockchain.server.cct.entity.dto.TradingOn;
import com.blockchain.server.cct.feign.QuantizedFeign;
import com.blockchain.server.cct.feign.UserFeign;
import com.blockchain.server.cct.mapper.PublishOrderMapper;
import com.blockchain.server.cct.service.CoinService;
import com.blockchain.server.cct.service.PublishOrderLogService;
import com.blockchain.server.cct.service.PublishOrderService;
import com.blockchain.server.cct.service.WalletService;
import com.codingapi.tx.annotation.TxTransaction;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class PublishOrderServiceImpl implements PublishOrderService {

    @Autowired
    private PublishOrderMapper orderMapper;
    @Autowired
    private PublishOrderLogService orderLogService;
    @Autowired
    private CoinService coinService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserFeign userFeign;
    @Autowired
    private QuantizedFeign quantizedFeign;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;

    //日志记录操作字段
    private static final String FIELD_ORDER_STATUS = "order_status";
    //数字常量
    private static final BigDecimal MINUS_ONE = new BigDecimal("-1");
    private static final int LOCK_WAIT_TIME = 5; //获取锁等待时间
    private static final int LOCK_LEASE_TIME = 5; //释放时间

    @Override
    public List<ListOrderResultDTO> listOrder(ListOrderParamDTO param) {
        //user信息不为空时，调用
        if (StringUtils.isNotBlank(param.getUserName()) || StringUtils.isNotBlank(param.getNickName())) {
            return listOrderByUser(param);
        }
        //user信息为空时，调用
        if (StringUtils.isBlank(param.getUserName()) && StringUtils.isBlank(param.getNickName())) {
            return listOrderByCondition(param);
        }

        return new ArrayList<>();
    }

    /***
     * 查询订单列表
     *
     * 查询条件带user信息时的业务逻辑
     *
     * @param param
     * @return
     */
    private List<ListOrderResultDTO> listOrderByUser(ListOrderParamDTO param) {
        //调用Feign查询用户id
        UserBaseInfoDTO userInfo = selectUserInfoByUserName(param.getUserName());
        //防空
        if (userInfo != null) param.setUserId(userInfo.getUserId());
        //查询对应用户的挂单列表
        List<ListOrderResultDTO> orders = orderMapper.listOrder(param);
        //设置用户的手机信息
        for (ListOrderResultDTO order : orders) {
            order.setUserName(userInfo.getMobilePhone());
            order.setRealName(userInfo.getRealName());
        }
        //返回
        return orders;
    }

    /***
     * 查询订单列表
     *
     * 查询条件没有user信息时的业务逻辑
     *
     * @param param
     * @return
     */
    private List<ListOrderResultDTO> listOrderByCondition(ListOrderParamDTO param) {
        //查询对应用户的挂单列表
        List<ListOrderResultDTO> orders = orderMapper.listOrder(param);
        //封装用户id集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListOrderResultDTO order : orders) {
            if (StringUtils.isNotBlank(order.getUserId())) {
                userIds.add(order.getUserId());
            }
        }
        //防止参数为空
        if (userIds.size() == 0) return orders;
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos == null) return orders;
        for (ListOrderResultDTO order : orders) {
            UserBaseInfoDTO userInfo = userInfos.get(order.getUserId());
            order.setUserName(userInfo != null ? userInfo.getMobilePhone() : "");
            order.setRealName(userInfo != null ? userInfo.getRealName() : "");
        }
        return orders;
    }

    @Override
    public PublishOrder selectOrderForUpdate(String orderId) {
        return orderMapper.selectOrderForUpdate(orderId);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public int cancel(String sysUserId, String ipAddr, String orderId) {
        //排他锁查询订单
        PublishOrder order = orderMapper.selectOrderForUpdate(orderId);
        //状态不为已撮合或者新建
        if (!order.getOrderStatus().equals(CCTConstant.STATUS_MATCH) &&
                !order.getOrderStatus().equals(CCTConstant.STATUS_NEW)) {
            throw new CCTException(CCTEnums.REPEAL_STATUS_ERROR);
        }
        //记录管理员操作日志
        inserOrderLog(sysUserId, ipAddr, orderId, FIELD_ORDER_STATUS, order.getOrderStatus(), CCTConstant.STATUS_CANCEL);

        if (getTradingOn(order.getCoinName(), order.getUnitName())) {
            ResultDTO<String> result = quantizedFeign.cancellations(orderId);
            if (result.getMsg().equals("OVER")) {
                //如果是OVER,说明量化没有此订单信息，可以直接撤销完成，走下面正常的逻辑
            } else if (result.getMsg().equals("SUCCESS")) {
                //如果是OVER,说明量化有此订单信息并且发起成功一个撤单请求，将订单改为撤单中状态，实际状态由火币推送处理
                order.setOrderStatus(CCTConstant.STATUS_CANCELING);
                order.setModifyTime(new Date());
                return orderMapper.updateByPrimaryKeySelective(order);
            } else if (result.getMsg().equals("FAIL")) {
                throw new CCTException(CCTEnums.ORDER_CANCEL_ERROR);
            }
        }

        //退钱
        updateCancelWallet(order);
        //撤销订单
        order.setModifyTime(new Date());
        order.setOrderStatus(CCTConstant.STATUS_CANCEL);
        //返回订单更新状态码
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row > 0) {
            Double price = Double.parseDouble(order.getUnitPrice().toString());
            //更新redis缓存中的订单数据
            updateRedisOrderList(price, order.getLastNum(), order.getCoinName(), order.getUnitName(), order.getOrderType());
            return row;
        } else {
            return row;
        }
    }

    /***
     * 插入管理员操作订单记录
     * @param sysUserId
     * @param ipAddr
     * @param orderId
     * @param field
     * @param beforeVal
     * @param afterVal
     */
    private void inserOrderLog(String sysUserId, String ipAddr, String orderId,
                               String field, String beforeVal, String afterVal) {
        PublishOrderLog orderLog = new PublishOrderLog();
        orderLog.setId(UUID.randomUUID().toString());
        orderLog.setSysUserId(sysUserId);
        orderLog.setIpAddress(ipAddr);
        orderLog.setOrderId(orderId);
        orderLog.setField(field);
        orderLog.setBeforeValue(beforeVal);
        orderLog.setAfterValue(afterVal);
        orderLog.setCreateTime(new Date());
        orderLogService.insertOrderLog(orderLog);
    }

    /***
     * 判断撤销订单发布类型，并判断订单类型解冻余额
     *
     * @param order
     * @return
     */
    private void updateCancelWallet(PublishOrder order) {
        //市价订单撤回时
        if (order.getPublishType().equals(CCTConstant.TYPE_MARKET)) {
            checkCancelOrderType(order);
        }

        //限价订单撤回时
        if (order.getPublishType().equals(CCTConstant.TYPE_LIMIT)) {
            checkCancelOrderType(order);
        }
    }

    /***
     * 判断撤销订单类型，并解冻余额
     */
    private void checkCancelOrderType(PublishOrder order) {
        //查询币种信息
        Coin coin = coinService.selectCoin(order.getCoinName(), order.getUnitName(), CCTConstant.STATUS_YES);

        BigDecimal amount = BigDecimal.ZERO; //退回的可用余额
        BigDecimal decut = BigDecimal.ZERO; //解冻的冻结余额
        String coinName = ""; //退回的币种钱包
        String coinNet = ""; //退回的币种主网标识

        //买单退回剩余交易额，单位是二级货币
        if (order.getOrderType().equals(CCTConstant.TYPE_BUY)) {
            amount = order.getLastTurnover();
            decut = amount.multiply(MINUS_ONE);
            coinName = order.getUnitName();
            coinNet = coin.getUnitNet();
        }
        //卖单退回剩余数量，单位是基本货币
        if (order.getOrderType().equals(CCTConstant.TYPE_SELL)) {
            amount = order.getLastNum();
            decut = amount.multiply(MINUS_ONE);
            coinName = order.getCoinName();
            coinNet = coin.getCoinNet();
        }

        //解冻余额
        walletService.handleBalance(order.getUserId(), order.getId(), coinName, coinNet, amount, decut);
    }

    /***
     * 根据账户查询用户信息
     * @param userName
     * @return
     */
    private UserBaseInfoDTO selectUserInfoByUserName(String userName) {
        ResultDTO resultDTO = userFeign.selectUserInfoByUserName(userName);
        return (UserBaseInfoDTO) resultDTO.getData();
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

    /**
     * 判断是否量化交易
     *
     * @param coinName
     * @param unitName
     * @return
     */
    public Boolean getTradingOn(String coinName, String unitName) {
        ResultDTO<TradingOn> resultDTO = quantizedFeign.getTradingOn(coinName, unitName);
        if (resultDTO == null) {
            throw new CCTException(CCTEnums.NET_ERROR);
        }
        if (resultDTO.getCode() != BaseConstant.REQUEST_SUCCESS) {
            throw new RPCException(resultDTO.getCode(), resultDTO.getMsg());
        }
        TradingOn tradingOn = resultDTO.getData();
        if (tradingOn == null) {
            return false;
        }
        if (tradingOn.getState().equalsIgnoreCase(QuantizedConstant.STATUS_CLOSEING)) {
            throw new CCTException(CCTEnums.QUANTIZED_CLOSING);
        }
        if (tradingOn.getState().equalsIgnoreCase(QuantizedConstant.STATUS_OPEN)) {
            return true;
        }
        return false;
    }

    /**
     * 撤单后将订单数据更新到redis中
     *
     * @param price
     * @param updateNum
     * @param coinName
     * @param unitName
     * @param tradingType
     */
    private void updateRedisOrderList(Double price, BigDecimal updateNum, String coinName, String unitName, String tradingType) {
        //订单数据的redisKey
        String redisOrderKey = RedisConstant.getOrderKey(tradingType, coinName, unitName);
        //redis是否存在对应的key
        Boolean flag = redisTemplate.hasKey(redisOrderKey);

        //redis存在key
        if (flag) {
            //加锁的key
            String redisOrderLockKey = RedisConstant.getLockOrderKey(tradingType, coinName, unitName);
            //是否成功获取锁标识
            boolean lockFlag = false;
            //加锁，进行添加数据
            while (!lockFlag) {
                //循环，直到获取锁为止
                lockFlag = RedissonTool.tryFairLock(redissonClient, redisOrderLockKey, LOCK_WAIT_TIME, LOCK_LEASE_TIME, TimeUnit.SECONDS);
                //拿到锁，处理数据
                if (lockFlag) {
                    //根据单价（sortSet排序分数）获取订单数据
                    Set redisOrder = redisTemplate.opsForZSet().rangeByScore(redisOrderKey, price, price);
                    //用迭代器更新数据
                    Iterator iterator = redisOrder.iterator();
                    //处理数据
                    while (iterator.hasNext()) {
                        MarketDTO order = (MarketDTO) iterator.next();
                        //更新剩余数量
                        order.setTotalLastNum(order.getTotalLastNum().subtract(updateNum));
                        //删除旧数据
                        redisTemplate.opsForZSet().removeRangeByScore(redisOrderKey, price, price);
                        //如果剩余数量大于0，添加数据
                        if (order.getTotalLastNum().compareTo(BigDecimal.ZERO) > 0) {
                            //覆盖数据
                            redisTemplate.opsForZSet().add(redisOrderKey, order, price);
                        }
                    }

                    //释放锁
                    RedissonTool.unFairLock(redissonClient, redisOrderLockKey);
                }
            }
        }
    }

}
