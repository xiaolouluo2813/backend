package com.blockchain.server.databot.service.impl;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.dto.user.UserBaseInfoDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.databot.common.constant.CommonConstant;
import com.blockchain.server.databot.common.enums.DataBotEnums;
import com.blockchain.server.databot.common.exception.DataBotExeption;
import com.blockchain.server.databot.dto.matchconfig.InsertMatchConfigParamDTO;
import com.blockchain.server.databot.dto.matchconfig.ListMatchConfigResultDTO;
import com.blockchain.server.databot.dto.matchconfig.UpdateMatchConfigParamDTO;
import com.blockchain.server.databot.entity.MatchConfig;
import com.blockchain.server.databot.feign.UserFeign;
import com.blockchain.server.databot.mapper.MatchConfigMapper;
import com.blockchain.server.databot.redis.MatchConfigCache;
import com.blockchain.server.databot.service.MatchConfigHandleLogService;
import com.blockchain.server.databot.service.MatchConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MatchConfigServiceImpl implements MatchConfigService {

    @Autowired
    private MatchConfigMapper matchConfigMapper;
    @Autowired
    private MatchConfigHandleLogService matchConfigHandleLogService;
    @Autowired
    private MatchConfigCache matchConfigCache;
    @Autowired
    private UserFeign userFeign;

    @Override
    public List<ListMatchConfigResultDTO> listConfig(String userName, String coinName, String unitName,
                                                     String status, String priceType) {
        if (StringUtils.isNotBlank(userName)) {
            //userName不为空时，调用
            return listConfigByUser(userName, coinName, unitName, status, priceType);
        } else {
            //userName为空时，调用
            return listConfigByCondition(coinName, unitName, status, priceType);
        }
    }

    /***
     * 查询撮合机器人列表
     *
     * 查询条件带userName时的业务逻辑
     *
     * @param userName
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    private List<ListMatchConfigResultDTO> listConfigByUser(String userName, String coinName, String unitName,
                                                            String status, String priceType) {
        //调用Feign查询用户id
        UserBaseInfoDTO userInfo = selectUserInfoByUserName(userName);
        //防空
        if (userInfo == null) {
            return new ArrayList<>();
        } else {
            //查询对应用户的挂单列表
            List<ListMatchConfigResultDTO> configs = matchConfigMapper.listConfig(userInfo.getUserId(), coinName, unitName, status, priceType);
            //设置用户的手机信息
            for (ListMatchConfigResultDTO config : configs) {
                config.setUnitName(userInfo.getMobilePhone());
            }
            //返回
            return configs;
        }
    }

    /***
     * 查询撮合机器人列表
     *
     * 查询条件不带userName时的业务逻辑
     *
     * @param coinName
     * @param unitName
     * @param status
     * @return
     */
    private List<ListMatchConfigResultDTO> listConfigByCondition(String coinName, String unitName, String status,
                                                                 String priceType) {
        //查询对应用户的挂单列表
        List<ListMatchConfigResultDTO> configs = matchConfigMapper.listConfig(null, coinName, unitName, status, priceType);
        //封装用户id集合，用于一次性查询用户信息
        Set userIds = new HashSet();
        //封装用户id
        for (ListMatchConfigResultDTO config : configs) {
            String userId = config.getUserId();
            if (StringUtils.isNotBlank(userId)) {
                userIds.add(userId);
            }
        }
        //防止参数为空
        if (userIds.size() == 0) {
            return configs;
        }
        //调用feign一次性查询用户信息
        Map<String, UserBaseInfoDTO> userInfos = listUserInfos(userIds);
        //防止返回用户信息为空
        if (userInfos.size() == 0) {
            return configs;
        }
        //循环添加用户数据
        for (ListMatchConfigResultDTO config : configs) {
            UserBaseInfoDTO userInfo = userInfos.get(config.getUserId());
            if (userInfo != null) {
                config.setUserName(userInfo.getMobilePhone());
            }
        }

        return configs;
    }

    @Override
    @Transactional
    public int insertConfig(String sysUserId, String ipAddress, InsertMatchConfigParamDTO paramDTO) {
        //新增时判断币对存不存在
        MatchConfig configIsExist = matchConfigMapper.selectByCoinAndUnit(paramDTO.getCoinName(), paramDTO.getUnitName());
        if (configIsExist != null) {
            throw new DataBotExeption(DataBotEnums.MATCH_CONFIG_EXIST);
        }
        UserBaseInfoDTO userBaseInfoDTO = selectUserInfoByUserName(paramDTO.getUserName());
        //判断新增的用户是否存在
        if (userBaseInfoDTO == null) {
            throw new DataBotExeption(DataBotEnums.USER_NULL);
        }
        //构建新增撮合配置对象
        MatchConfig matchConfig = newInsertConfigObj(paramDTO, userBaseInfoDTO.getUserId());
        //新建撮合配置
        int row = matchConfigMapper.insertSelective(matchConfig);
        if (row == 1) {
            //新建撮合配置操作记录
            matchConfigHandleLogService.insertHandleLog(matchConfig, null, sysUserId, ipAddress, CommonConstant.INSERT);
            return row;
        } else {
            throw new BaseException(BaseResultEnums.BUSY);
        }
    }

    @Override
    @Transactional
    public int updateConfig(String sysUserId, String ipAddress, UpdateMatchConfigParamDTO paramDTO) {
        //判断配置是否存在
        MatchConfig matchConfig = checkConfigNull(paramDTO.getId());

        //账户参数不为空时，查询并设置userId
        String userName = paramDTO.getUserName();
        if (StringUtils.isNotBlank(userName)) {
            UserBaseInfoDTO userBaseInfoDTO = selectUserInfoByUserName(userName);
            //判断新增的用户是否存在
            if (userBaseInfoDTO == null) {
                throw new DataBotExeption(DataBotEnums.USER_NULL);
            }
            paramDTO.setUserId(userBaseInfoDTO.getUserId());
        }

        //更新
        int row = matchConfigMapper.updateConfig(paramDTO, new Date());
        if (row == 1) {
            //新建撮合配置操作记录
            matchConfigHandleLogService.insertHandleLog(matchConfig, paramDTO, sysUserId, ipAddress, CommonConstant.UPDATE);
            //删除缓存中的Key
            String key = matchConfigCache.getKey(matchConfig.getCoinName(), matchConfig.getUnitName());
            matchConfigCache.deleteValue(key);
            return row;
        } else {
            throw new BaseException(BaseResultEnums.BUSY);
        }
    }

    @Override
    @Transactional
    public int deleteConfig(String sysUserId, String ipAddress, String id) {
        //判断配置是否存在
        MatchConfig matchConfig = checkConfigNull(id);

        //删除配置
        int row = matchConfigMapper.deleteByPrimaryKey(id);
        if (row == 1) {
            //新建撮合配置操作记录
            matchConfigHandleLogService.insertHandleLog(matchConfig, null, sysUserId, ipAddress, CommonConstant.DELETE);
            //删除缓存中的Key
            String key = matchConfigCache.getKey(matchConfig.getCoinName(), matchConfig.getUnitName());
            matchConfigCache.deleteValue(key);
            return row;
        } else {
            throw new BaseException(BaseResultEnums.BUSY);
        }
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

    /***
     * 新增时构造撮合配置对象
     * @param paramDTO
     * @param userId
     * @return
     */
    private MatchConfig newInsertConfigObj(InsertMatchConfigParamDTO paramDTO, String userId) {
        MatchConfig matchConfig = new MatchConfig();
        Date now = new Date();
        matchConfig.setId(UUID.randomUUID().toString());
        matchConfig.setUserId(userId);
        matchConfig.setCoinName(paramDTO.getCoinName());
        matchConfig.setUnitName(paramDTO.getUnitName());
        matchConfig.setMinPrice(paramDTO.getMinPrice());
        matchConfig.setMaxPrice(paramDTO.getMaxPrice());
        matchConfig.setMinPercent(paramDTO.getMinPercent());
        matchConfig.setMaxPercent(paramDTO.getMaxPercent());
        //默认为禁用状态
        matchConfig.setStatus(CommonConstant.NO);
        matchConfig.setCreateTime(now);
        matchConfig.setModifyTime(now);
        matchConfig.setPriceType(paramDTO.getPriceType());
        return matchConfig;
    }

    /***
     * 查询并检查撮合配置是否为空
     * @param id
     * @return
     */
    private MatchConfig checkConfigNull(String id) {
        MatchConfig matchConfig = matchConfigMapper.selectByPrimaryKey(id);
        //判断更新的配置存不存在
        if (matchConfig == null) {
            throw new DataBotExeption(DataBotEnums.MATCH_CONFIG_NULL);
        }
        return matchConfig;
    }
}
