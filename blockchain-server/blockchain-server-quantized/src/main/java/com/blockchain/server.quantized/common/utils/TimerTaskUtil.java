package com.blockchain.server.quantized.common.utils;

import com.blockchain.server.quantized.scheduleTask.CancelTimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author: Liusd
 * @create: 2019-05-07 15:24
 **/
@Component
public class TimerTaskUtil {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Autowired
    private CancelTimerTask cancelTimerTask;

    public void startCron() {
        if (future == null) {
            //三秒一次执行定时器
            String cron = "0/3 * * * * ?";
            future = threadPoolTaskScheduler.schedule(cancelTimerTask, new CronTrigger(cron));
        }
    }

    public void stopCron() {
        if (future != null) {
            future.cancel(true);
            future = null;
        }
    }
}
