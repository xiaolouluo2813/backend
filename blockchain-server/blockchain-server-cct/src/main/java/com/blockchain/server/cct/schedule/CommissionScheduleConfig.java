package com.blockchain.server.cct.schedule;

import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.entity.Config;
import com.blockchain.server.cct.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class CommissionScheduleConfig {

    @Autowired
    private ConfigService configService;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                //启动项目时，判断是否开启定时器
                openCommissionSchedule();
            }
        };
    }

    /***
     * 打开佣金发放定时器
     */
    private void openCommissionSchedule() {
        Config config = configService.selectByKey(CCTConstant.TYPE_COMMISSION_ISSUE_TIME);
        //配置不为空并且状态是开启
        if (config != null && config.getDataStatus().equals(CCTConstant.STATUS_YES)) {
            configService.openCommissionSchedule(config.getDataValue());
        }
    }
}
