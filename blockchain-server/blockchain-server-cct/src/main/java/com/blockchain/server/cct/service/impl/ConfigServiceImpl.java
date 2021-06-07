package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.common.constant.CCTConstant;
import com.blockchain.server.cct.common.enums.CCTEnums;
import com.blockchain.server.cct.common.exception.CCTException;
import com.blockchain.server.cct.entity.Config;
import com.blockchain.server.cct.entity.ConfigLog;
import com.blockchain.server.cct.mapper.ConfigMapper;
import com.blockchain.server.cct.schedule.CommissionSchedule;
import com.blockchain.server.cct.service.ConfigLogService;
import com.blockchain.server.cct.service.ConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private ConfigLogService logService;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> future;

    @Autowired
    private CommissionSchedule commissionSchedule;

    @Override
    @Transactional
    public int updateConfig(String sysUserId, String ipAddr, String tag, String key, String val, String status) {
        Date now = new Date();
        Config config = selectByKey(key);

        ConfigLog log = new ConfigLog();
        log.setIpAddress(ipAddr);
        log.setSysUserId(sysUserId);
        log.setId(UUID.randomUUID().toString());
        log.setDataKey(key);
        log.setDataValue(config.getDataValue());
        log.setDataValueBefore(config.getDataValue());
        log.setDataStatus(config.getDataStatus());
        log.setDataStatusBefore(config.getDataStatus());
        log.setDataTag(config.getDataTag());
        log.setCreateTime(now);

        if (StringUtils.isNotBlank(val)) {
            log.setDataValue(val);
            config.setDataValue(val);
        }
        if (StringUtils.isNotBlank(status)) {
            log.setDataStatus(status);
            config.setDataStatus(status);
        }
        if (StringUtils.isNotBlank(tag)) {
            log.setDataTag(tag);
            config.setDataTag(tag);
        }
        config.setModifyTime(now);

        logService.insertConfigLog(log);
        return configMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    @Transactional
    public int updateCommissionIssueTime(String sysUserId, String ipAddr, String type, Integer day, Integer hour, String status) {
        //type为空，更新状态
        if (StringUtils.isBlank(type)) {
            //更新状态
            int row = updateConfig(sysUserId, ipAddr, null, CCTConstant.TYPE_COMMISSION_ISSUE_TIME, null, status);
            //查询配置值
            Config config = configMapper.selectByKey(CCTConstant.TYPE_COMMISSION_ISSUE_TIME);
            //判断开启或关闭
            if (status.equals(CCTConstant.STATUS_YES)) {
                //先关，再重新开启
                this.openCommissionSchedule(config.getDataValue());
            }
            if (status.equals(CCTConstant.STATUS_NO)) {
                //关闭定时器
                this.closeCommissionSchedule();
            }
            return row;
        } else {
            //计算cron表达式
            String cron = createCronStr(type, day, hour);
            //更新
            return updateConfig(sysUserId, ipAddr, null, CCTConstant.TYPE_COMMISSION_ISSUE_TIME, cron, null);
        }
    }

    @Override
    public List<Config> listConfig() {
        return configMapper.selectAll();
    }

    @Override
    public Config selectByKey(String key) {
        return configMapper.selectByKey(key);
    }

    /***
     * 开启佣金定时器
     * @param cron
     *
     */
    @Override
    public void openCommissionSchedule(String cron) {
        //先关闭定时器
        this.closeCommissionSchedule();
        //打开定时器
        future = threadPoolTaskScheduler.schedule(commissionSchedule, new CronTrigger(cron));
    }

    /***
     * 关闭佣金定时器
     */
    @Override
    public void closeCommissionSchedule() {
        if (future != null) {
            future.cancel(true);
        }
    }


    private static final String DAY = "DAY";
    private static final String MONTH = "MONTH";
    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 28;
    private static final int MIN_HOUR = 0;
    private static final int MAX_HOUR = 23;

    /***
     *
     * @param type
     * @param day
     * @param hour
     * @return
     */
    private String createCronStr(String type, Integer day, Integer hour) {
        //校验参数
        checkCronParam(type, day, hour);

        //默认凌晨12点01分发放
        String cron = "0 1 0 * * ?";

        //按日发放
        if (type.equals(DAY)) {
            cron = "0 0 " + hour + " * * ?";
        }
        //按月发放
        if (type.equals(MONTH)) {
            cron = "0 0 " + hour + " " + day + " * ?";
        }
        return cron;
    }

    /***
     * 检查生成表达式的参数
     * @param type
     * @param day
     * @param hour
     */
    private void checkCronParam(String type, Integer day, Integer hour) {
        //小时数是否为空
        if (hour == null) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_HOUR_NULL);
        }
        //小时数是否合法
        if (hour < MIN_HOUR || hour > MAX_HOUR) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_HOUR_ERROR);
        }

        //按月发放
        if (type.equals(MONTH)) {
            //日期是否为空
            if (day == null) {
                throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_DAY_NULL);
            }
            //日期是否合法
            if (day < MIN_DAY || hour > MAX_DAY) {
                throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_DAY_ERROR);
            }
        }

        //类型不等于按日并且不等于按月
        if (!type.equals(DAY) && !type.equals(MONTH)) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_TYPE_ERROR);
        }
    }
}
