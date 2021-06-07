package com.blockchain.server.otc.service.impl;

        import com.alibaba.fastjson.JSONObject;
        import com.blockchain.common.base.constant.AppType;
        import com.blockchain.common.base.constant.PushConstants;
        import com.blockchain.common.base.enums.PushEnums;
        import com.blockchain.server.otc.common.enums.*;
        import com.blockchain.server.otc.common.exception.OtcException;
        import com.blockchain.server.otc.dto.appeal.ListAppealResultDTO;
        import com.blockchain.server.otc.entity.Ad;
        import com.blockchain.server.otc.entity.Appeal;
        import com.blockchain.server.otc.entity.Order;
        import com.blockchain.server.otc.feign.PushFeign;
        import com.blockchain.server.otc.mapper.AppealMapper;
        import com.blockchain.server.otc.service.*;
        import com.codingapi.tx.annotation.ITxTransaction;
        import com.codingapi.tx.annotation.TxTransaction;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.util.LinkedMultiValueMap;
        import org.springframework.util.MultiValueMap;
        import org.springframework.web.client.RestTemplate;

        import java.math.BigDecimal;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

@Service
public class AppealServiceImpl implements AppealService, ITxTransaction {

    @Autowired
    private AppealMapper appealMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AdService adService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private BillService billService;
    @Autowired
    private AppealHandleLogService appealHandleLogService;
    @Autowired
    private PushFeign pushFeign;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<ListAppealResultDTO> listAppeal(String orderNumber, String status, String beginTime, String endTime) {
        return appealMapper.listAppeal(orderNumber, status, beginTime, endTime);
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void handleFinishOrder(String appealId, String sysUserId, String ipAddress, String remark) {
        //查询申诉记录
        Appeal appeal = appealMapper.selectByPrimaryKey(appealId);
        //判断申诉记录状态是否是新建
        if (!appeal.getStatus().equals(AppealEnums.STATUS_NEW.getValue())) {
            throw new OtcException(OtcEnums.APPEAL_STATUS_NOT_NEW);
        }
        //根据订单号查询订单
        Order order = orderService.selectByOrderNumber(appeal.getOrderNumber());
        //排他锁查询订单
        Order orderForUpdate = orderService.selectByIdForUpdate(order.getId());
        //判断订单是否是申诉中
        if (!orderForUpdate.getOrderStatus().equals(OrderEnums.STATUS_APPEAL.getValue())) {
            throw new OtcException(OtcEnums.APPEAL_ORDER_STATUS_NOT_APPEAL);
        }
        //查询广告
        Ad ad = adService.selectById(order.getAdId());

        //处理双方资金变动
        handleFinishOrderWallet(orderForUpdate, ad);
        //订单状态为完成
        orderForUpdate.setOrderStatus(OrderEnums.STATUS_FINISH.getValue());
        orderForUpdate.setModifyTime(new Date());
        orderService.updateByPrimaryKeySelective(orderForUpdate);

        //更新申诉记录
        appeal.setStatus(AppealEnums.STATUS_HANDLE.getValue());
        appeal.setModifyTime(new Date());
        appealMapper.updateByPrimaryKeySelective(appeal);

        //新增申诉操作日志
        appealHandleLogService.insertAppealHandleLog(sysUserId, ipAddress, orderForUpdate.getOrderNumber(),
                OrderEnums.STATUS_FINISH.getValue(), remark);

        //广告是否可以继续交易
        checkAdCanFinish(ad.getId());

        //申诉结束，通知订单双方用户
        appealFinishAndPushToUser(order.getSellUserId(), order.getBuyUserId(), order.getId());
    }

    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public void handleCancelOrder(String appealId, String sysUserId, String ipAddress, String remark) {
        //查询申诉记录
        Appeal appeal = appealMapper.selectByPrimaryKey(appealId);
        //判断申诉记录状态是否是新建
        if (!appeal.getStatus().equals(AppealEnums.STATUS_NEW.getValue())) {
            throw new OtcException(OtcEnums.APPEAL_STATUS_NOT_NEW);
        }
        //根据订单号查询订单
        Order order = orderService.selectByOrderNumber(appeal.getOrderNumber());
        //排他锁查询订单
        Order orderForUpdate = orderService.selectByIdForUpdate(order.getId());
        //判断订单是否是申诉中
        if (!orderForUpdate.getOrderStatus().equals(OrderEnums.STATUS_APPEAL.getValue())) {
            throw new OtcException(OtcEnums.APPEAL_ORDER_STATUS_NOT_APPEAL);
        }
        //排他锁查询广告
        Ad ad = adService.selectByIdForUpdate(order.getAdId());

        //订单是卖单，解冻余额
        if (order.getOrderType().equals(OrderEnums.TYPE_SELL.getValue())) {
            //交易数量
            BigDecimal amount = order.getAmount();
            //手续费
            BigDecimal serviceCharge = amount.multiply(order.getChargeRatio());
            //增加可用
            BigDecimal freeBalance = amount.add(serviceCharge);
            //解冻余额
            BigDecimal freezeBalance = freeBalance.multiply(new BigDecimal("-1"));
            //解冻余额
            walletService.handleBalance(order.getSellUserId(), order.getOrderNumber(), order.getCoinName(), order.getUnitName(), freeBalance, freezeBalance);
            //记录资金变动
            billService.insertBill(order.getSellUserId(), order.getOrderNumber(), freeBalance, freezeBalance, BillEnums.TYPE_CANCEL.getValue(), order.getCoinName());
        }
        //订单状态改为失效
        order.setOrderStatus(OrderEnums.STATUS_CANCEL.getValue());
        order.setModifyTime(new Date());
        orderService.updateByPrimaryKeySelective(order);

        //更新广告剩余数量
        ad.setLastNum(ad.getLastNum().add(order.getAmount()));
        ad.setMaxLimit(ad.getLastNum().multiply(ad.getPrice()).setScale(0, BigDecimal.ROUND_DOWN));
        ad.setModifyTime(new Date());
        adService.updateByPrimaryKeySelective(ad);

        //更新申诉记录
        appeal.setStatus(AppealEnums.STATUS_HANDLE.getValue());
        appeal.setModifyTime(new Date());
        appealMapper.updateByPrimaryKeySelective(appeal);

        //新增申诉操作日志
        appealHandleLogService.insertAppealHandleLog(sysUserId, ipAddress, orderForUpdate.getOrderNumber(),
                OrderEnums.STATUS_CANCEL.getValue(), remark);

        //判断广告是否可以已完成
        checkAdCanFinish(ad.getId());

        //申诉结束，通知订单双方用户
        appealFinishAndPushToUser(order.getSellUserId(), order.getBuyUserId(), order.getId());
    }

    /***
     * 处理完成订单的双方资金
     * @param order
     * @param ad
     */
    private void handleFinishOrderWallet(Order order, Ad ad) {
        //交易数量
        BigDecimal amount = order.getAmount();
        //手续费
        BigDecimal serviceCharge = BigDecimal.ZERO;
        //买家实际获得数量
        BigDecimal realAmount = BigDecimal.ZERO;
        //卖家解冻数量
        BigDecimal decut = BigDecimal.ZERO;

        //订单为买单-（买方是订单方，卖方是广告方）
        if (order.getOrderType().equals(OrderEnums.TYPE_BUY.getValue())) {
            //买方扣除的订单手续费
            serviceCharge = amount.multiply(order.getChargeRatio());
            //买家实际获得数量
            realAmount = amount.subtract(serviceCharge);
            //卖家解冻数量
            decut = amount.add(amount.multiply(ad.getChargeRatio())).multiply(new BigDecimal("-1"));
        }

        //订单为卖单-（买方是广告方，卖方是订单方）
        if (order.getOrderType().equals(OrderEnums.TYPE_SELL.getValue())) {
            //买方扣除的订单手续费
            serviceCharge = amount.multiply(ad.getChargeRatio());
            //买家实际获得数量
            realAmount = amount.subtract(serviceCharge);
            //卖家解冻数量
            decut = amount.add(amount.multiply(order.getChargeRatio())).multiply(new BigDecimal("-1"));
        }

        //买家加款
        walletService.handleRealBalance(order.getBuyUserId(), order.getOrderNumber(), order.getCoinName(), order.getUnitName(), realAmount, BigDecimal.ZERO, serviceCharge);
        //记录资金变动
        billService.insertBill(order.getBuyUserId(), order.getOrderNumber(), realAmount, BigDecimal.ZERO, BillEnums.TYPE_MARK.getValue(), order.getCoinName());
        //卖家解冻
        walletService.handleRealBalance(order.getSellUserId(), order.getOrderNumber(), order.getCoinName(), order.getUnitName(), BigDecimal.ZERO, decut, BigDecimal.ZERO);
        //记录资金变动
        billService.insertBill(order.getSellUserId(), order.getOrderNumber(), BigDecimal.ZERO, decut, BillEnums.TYPE_MARK.getValue(), order.getCoinName());
    }

    /***
     * 判断广告是否能结束
     * @param adId
     */
    private void checkAdCanFinish(String adId) {
        //查询广告伞下是否还有未完成订单
        boolean unfinished = orderService.checkOrdersUnfinished(adId);

        //排他锁查询广告
        Ad ad = adService.selectByIdForUpdate(adId);

        //剩余交易额不足以交易
        if ((ad.getLastNum().multiply(ad.getPrice())).compareTo(ad.getMinLimit()) < 0) {
            //有未完成订单，下架
            if (unfinished) {
                ad.setAdStatus(AdEnums.STATUS_PENDING.getValue());
            } else {//没有未完成订单
                //广告是卖出类型时 并且 有剩余数量时，解冻余额
                if (ad.getAdType().equals(AdEnums.TYPE_SELL.getValue()) && ad.getLastNum().compareTo(BigDecimal.ZERO) > 0) {
                    //广告手续费
                    BigDecimal serviceCharge = ad.getLastNum().multiply(ad.getChargeRatio());
                    //增加可用
                    BigDecimal freeBalance = ad.getLastNum().add(serviceCharge);
                    //扣除冻结
                    BigDecimal freezeBalance = freeBalance.multiply(new BigDecimal("-1"));
                    //解冻余额
                    walletService.handleBalance(ad.getUserId(), ad.getAdNumber(), ad.getCoinName(), ad.getUnitName(), freeBalance, freezeBalance);
                    //记录资金变动
                    billService.insertBill(ad.getUserId(), ad.getAdNumber(), freeBalance, freezeBalance, BillEnums.TYPE_CANCEL.getValue(), ad.getCoinName());
                }
                //已完成
                ad.setAdStatus(AdEnums.STATUS_FINISH.getValue());
            }
        } else {
            //还有剩余剩余数量
            //有未完成订单，设置为进行中
            if (unfinished) {
                ad.setAdStatus(AdEnums.STATUS_UNDERWAY.getValue());
            } else {
                //没有未完成订单，设置为挂单中
                ad.setAdStatus(AdEnums.STATUS_DEFAULT.getValue());
            }
        }

        ad.setModifyTime(new Date());
        //更新广告
        adService.updateByPrimaryKeySelective(ad);
    }

    /***
     * 发送手机消息通知（个推消息推送API）
     * @param userId
     * @param orderId
     * @param pushType
     */
    private void pushToSingle(String userId, String orderId, String pushType) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(PushConstants.ORDER_ID, orderId);
        payload.put(PushConstants.PUSH_TYPE, pushType);
        pushFeign.pushToSingle(userId, pushType, payload);
    }

    /***
     * 申诉结束后推送消息通知订单双方用户
     * @param sellUserId
     * @param buyUserId
     * @param orderId
     */
    private void appealFinishAndPushToUser(String sellUserId, String buyUserId, String orderId) {
        pushToSingle(sellUserId, orderId, PushEnums.OTC_ORDER_APPEAL_FINISH.getPushType());
        pushToSingle(buyUserId, orderId, PushEnums.OTC_ORDER_APPEAL_FINISH.getPushType());
    }
}
