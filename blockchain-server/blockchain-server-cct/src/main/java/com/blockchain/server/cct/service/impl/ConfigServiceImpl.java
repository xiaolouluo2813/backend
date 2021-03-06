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
        //type?????????????????????
        if (StringUtils.isBlank(type)) {
            //????????????
            int row = updateConfig(sysUserId, ipAddr, null, CCTConstant.TYPE_COMMISSION_ISSUE_TIME, null, status);
            //???????????????
            Config config = configMapper.selectByKey(CCTConstant.TYPE_COMMISSION_ISSUE_TIME);
            //?????????????????????
            if (status.equals(CCTConstant.STATUS_YES)) {
                //????????????????????????
                this.openCommissionSchedule(config.getDataValue());
            }
            if (status.equals(CCTConstant.STATUS_NO)) {
                //???????????????
                this.closeCommissionSchedule();
            }
            return row;
        } else {
            //??????cron?????????
            String cron = createCronStr(type, day, hour);
            //??????
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
     * ?????????????????????
     * @param cron
     *
     */
    @Override
    public void openCommissionSchedule(String cron) {
        //??????????????????
        this.closeCommissionSchedule();
        //???????????????
        future = threadPoolTaskScheduler.schedule(commissionSchedule, new CronTrigger(cron));
    }

    /***
     * ?????????????????????
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
        //????????????
        checkCronParam(type, day, hour);

        //????????????12???01?????????
        String cron = "0 1 0 * * ?";

        //????????????
        if (type.equals(DAY)) {
            cron = "0 0 " + hour + " * * ?";
        }
        //????????????
        if (type.equals(MONTH)) {
            cron = "0 0 " + hour + " " + day + " * ?";
        }
        return cron;
    }

    /***
     * ??????????????????????????????
     * @param type
     * @param day
     * @param hour
     */
    private void checkCronParam(String type, Integer day, Integer hour) {
        //?????????????????????
        if (hour == null) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_HOUR_NULL);
        }
        //?????????????????????
        if (hour < MIN_HOUR || hour > MAX_HOUR) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_HOUR_ERROR);
        }

        //????????????
        if (type.equals(MONTH)) {
            //??????????????????
            if (day == null) {
                throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_DAY_NULL);
            }
            //??????????????????
            if (day < MIN_DAY || hour > MAX_DAY) {
                throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_DAY_ERROR);
            }
        }

        //??????????????????????????????????????????
        if (!type.equals(DAY) && !type.equals(MONTH)) {
            throw new CCTException(CCTEnums.COMMISSION_ISSUE_TIME_TYPE_ERROR);
        }
    }
}
