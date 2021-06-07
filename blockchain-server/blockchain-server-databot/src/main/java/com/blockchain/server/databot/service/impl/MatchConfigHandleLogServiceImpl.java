package com.blockchain.server.databot.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigHandleLogResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;
import com.blockchain.server.databot.entity.MatchConfig;
import com.blockchain.server.databot.entity.MatchConfigHandleLog;
import com.blockchain.server.databot.feign.UserFeign;
import com.blockchain.server.databot.mapper.MatchConfigHandleLogMapper;
import com.blockchain.server.databot.service.MatchConfigHandleLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MatchConfigHandleLogServiceImpl implements MatchConfigHandleLogService {

    @Autowired
    private MatchConfigHandleLogMapper configHandleLogMapper;
    @Autowired
    private UserFeign userFeign;

    @Override
    @Transactional
    public int insertHandleLog(MatchConfig config, UpdateMatchConfigParamDTO updateConfig, String sysUserId,
                               String ipAddress, String handleType) {
        MatchConfigHandleLog configHandleLog = new MatchConfigHandleLog();
        configHandleLog.setId(UUID.randomUUID().toString());
        configHandleLog.setSysUserId(sysUserId);
        configHandleLog.setIpAddress(ipAddress);

        //更新时，记录数据
        if (handleType.equals(CommonConstant.UPDATE)) {
            updateSetHandleLog(configHandleLog, config, updateConfig);
        }

        //删除时，记录数据
        if (handleType.equals(CommonConstant.DELETE)) {
            deleteSetHandleLog(configHandleLog, config);
        }

        //新建时，记录数据
        if (handleType.equals(CommonConstant.INSERT)) {
            insertSetHandleLog(configHandleLog, config);
        }

        configHandleLog.setCreateTime(new Date());
        configHandleLog.setHandleType(handleType);

        return configHandleLogMapper.insertSelective(configHandleLog);
    }

    /***
     * 更新时记录的数据
     * @param configHandleLog
     * @param config
     * @param updateConfig
     */
    private void updateSetHandleLog(MatchConfigHandleLog configHandleLog, MatchConfig config,
                                    UpdateMatchConfigParamDTO updateConfig) {
        configHandleLog.setBeforeUserId(config.getUserId());
        configHandleLog.setBeforeCoinName(config.getCoinName());
        configHandleLog.setBeforeUnitName(config.getUnitName());
        configHandleLog.setBeforeMinPrice(config.getMinPrice());
        configHandleLog.setBeforeMaxPrice(config.getMaxPrice());
        configHandleLog.setBeforeMinPercent(config.getMinPercent());
        configHandleLog.setBeforeMaxPercent(config.getMaxPercent());
        configHandleLog.setBeforePriceType(config.getPriceType());
        configHandleLog.setBeforeStatus(config.getStatus());

        configHandleLog.setAfterUserId(updateConfig.getUserId());
        configHandleLog.setAfterCoinName(updateConfig.getCoinName());
        configHandleLog.setAfterUnitName(updateConfig.getUnitName());
        configHandleLog.setAfterMinPrice(updateConfig.getMinPrice());
        configHandleLog.setAfterMaxPrice(updateConfig.getMaxPrice());
        configHandleLog.setAfterMinPercent(updateConfig.getMinPercent());
        configHandleLog.setAfterMaxPercent(updateConfig.getMaxPercent());
        configHandleLog.setAfterPriceType(updateConfig.getPriceType());
        configHandleLog.setAfterStatus(updateConfig.getStatus());
    }

    /***
     * 新增时，记录的数据
     * @param configHandleLog
     * @param config
     */
    private void insertSetHandleLog(MatchConfigHandleLog configHandleLog, MatchConfig config) {
        configHandleLog.setAfterUserId(config.getUserId());
        configHandleLog.setAfterCoinName(config.getCoinName());
        configHandleLog.setAfterUnitName(config.getUnitName());
        configHandleLog.setAfterMinPrice(config.getMinPrice());
        configHandleLog.setAfterMaxPrice(config.getMaxPrice());
        configHandleLog.setAfterMinPercent(config.getMinPercent());
        configHandleLog.setAfterMaxPercent(config.getMaxPercent());
        configHandleLog.setAfterPriceType(config.getPriceType());
        configHandleLog.setAfterStatus(config.getStatus());
    }

    /***
     * 删除时，记录的数据
     * @param configHandleLog
     * @param config
     */
    private void deleteSetHandleLog(MatchConfigHandleLog configHandleLog, MatchConfig config) {
        configHandleLog.setBeforeUserId(config.getUserId());
        configHandleLog.setBeforeCoinName(config.getCoinName());
        configHandleLog.setBeforeUnitName(config.getUnitName());
        configHandleLog.setBeforeMinPrice(config.getMinPrice());
        configHandleLog.setBeforeMaxPrice(config.getMaxPrice());
        configHandleLog.setBeforeMinPercent(config.getMinPercent());
        configHandleLog.setBeforeMaxPercent(config.getMaxPercent());
        configHandleLog.setBeforePriceType(config.getPriceType());
        configHandleLog.setBeforeStatus(config.getStatus());
    }

    @Override
    public List<ListMatchConfigHandleLogResultDTO> listHandleLog(String handleType, String beginTime, String endTime) {
        List<ListMatchConfigHandleLogResultDTO> logs = configHandleLogMapper.listHandleLog(handleType, beginTime, endTime);
        //封装用户id集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMatchConfigHandleLogResultDTO log : logs) {
            String beforeUserId = log.getBeforeUserId();
            String afterUserId = log.getAfterUserId();
            if (StringUtils.isNotBlank(beforeUserId)) {
                userIds.add(beforeUserId);
            }
            if (StringUtils.isNotBlank(afterUserId)) {
                userIds.add(afterUserId);
            }
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return logs;
        }
        //循环添加用户数据
        for (ListMatchConfigHandleLogResultDTO log : logs) {
            UserBaseInfoDTO beforeUser = userInfos.get(log.getBeforeUserId());
            UserBaseInfoDTO afterUser = userInfos.get(log.getAfterUserId());
            if (beforeUser != null) {
                log.setBeforeUserName(beforeUser.getMobilePhone());
            }
            if (afterUser != null) {
                log.setAfterUserName(afterUser.getMobilePhone());
            }
        }

        return logs;
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
