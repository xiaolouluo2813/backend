package com.blockchain.server.quantized.scheduleTask;

import com.blockchain.server.quantized.common.constant.TradingOnConstant;
import com.blockchain.server.quantized.common.utils.TimerTaskUtil;
import com.blockchain.server.quantized.entity.TradingOn;
import com.blockchain.server.quantized.service.TradingOnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 取消订阅交易对定时器
 */
@Component
public class CancelTimerTask implements Runnable {
    @Autowired
    private TradingOnService tradingOnService;
    @Autowired
    private TimerTaskUtil timerTaskUtil;

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(CancelTimerTask.class);
    @Override

    public void run() {
        LOG.info("定时任务开启！");
        //全部取消订阅中的交易对
        List<TradingOn> tradingOns = tradingOnService.list(TradingOnConstant.STATE_CANCELING);
        if (tradingOns!=null && tradingOns.size() > 0){
            for (TradingOn trading : tradingOns){
                tradingOnService.updateStateByCancel(trading);
            }
        }else{
            //关闭定时器
            timerTaskUtil.stopCron();
        }
    }
}
