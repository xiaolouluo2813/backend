package com.blockchain.server.databot.service.impl;

import com.blockchain.server.databot.common.enums.DataBotEnums;
import com.blockchain.server.databot.common.exception.DataBotExeption;
import com.blockchain.server.databot.dto.currencyconfig.InsertConfigParamDTO;
import com.blockchain.server.databot.dto.currencyconfig.ListConfigResultDTO;
import com.blockchain.server.databot.dto.currencyconfig.UpdateConfigParamDTO;
import com.blockchain.server.databot.entity.CurrencyConfig;
import com.blockchain.server.databot.mapper.CurrencyConfigMapper;
import com.blockchain.server.databot.redis.CurrencyConfigCache;
import com.blockchain.server.databot.service.CurrencyConfigHandleLogService;
import com.blockchain.server.databot.service.CurrencyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CurrencyConfigServiceImpl implements CurrencyConfigService {

    @Autowired
    private CurrencyConfigMapper currencyConfigMapper;
    @Autowired
    private CurrencyConfigHandleLogService currencyConfigHandleLogService;
    @Autowired
    private CurrencyConfigCache currencyConfigCache;

    @Override
    public List<ListConfigResultDTO> listConfig(String currencyPair, String status) {
        return currencyConfigMapper.listConfig(currencyPair, status);
    }

    @Override
    public CurrencyConfig getConfigByCurrencyPair(String currencyPair) {
        return currencyConfigMapper.getConfigByCurrencyPair(currencyPair);
    }

    @Override
    @Transactional
    public int insertConfig(InsertConfigParamDTO paramDTO, String sysUserId, String ipAddress) {
        CurrencyConfig hasConfig = getConfigByCurrencyPair(paramDTO.getCurrencyPair());
        //判断新增配置是否存在
        if (hasConfig != null) {
            throw new DataBotExeption(DataBotEnums.CURRENCY_PAIR_EXIST);
        }

        //构造数据
        CurrencyConfig config = new CurrencyConfig();
        Date now = new Date();
        config.setId(UUID.randomUUID().toString());
        config.setCurrencyPair(paramDTO.getCurrencyPair());

        config.setKchangePercent(paramDTO.getKchangePercent());
        config.setKmaxChangePercent(paramDTO.getKmaxChangePercent());
        config.setKdayTotalAmount(paramDTO.getKdayTotalAmount());
        config.setKmaxPrice(paramDTO.getKmaxPrice());
        config.setKminPrice(paramDTO.getKminPrice());

        config.setBuyPricePercent(paramDTO.getBuyPricePercent());
        config.setBuyTotalAmount(paramDTO.getBuyTotalAmount());
        config.setBuyMaxPrice(paramDTO.getBuyMaxPrice());
        config.setBuyMinPrice(paramDTO.getBuyMinPrice());

        config.setSellPricePercent(paramDTO.getSellPricePercent());
        config.setSellTotalAmount(paramDTO.getSellTotalAmount());
        config.setSellMaxPrice(paramDTO.getSellMaxPrice());
        config.setSellMinPrice(paramDTO.getSellMinPrice());

        config.setPriceType(paramDTO.getPriceType());
        config.setStatus(paramDTO.getStatus());
        config.setCreateTime(now);
        config.setModifyTime(now);

        //插入
        int row = currencyConfigMapper.insertSelective(config);
        //插入成功时
        if (row == 1) {
            //插入新增配置操作日志记录
            currencyConfigHandleLogService.insertInsertHandleLog(config, sysUserId, ipAddress);
            //将新增的配置信息放入缓存中
            setConfigListRedisHash(config);
            //如果修改过K线发行量
            if (paramDTO.getKdayTotalAmount() != null) {
                //删除对应的K线数据Key
                removeKDAmountCache(config.getCurrencyPair());
            }
        }
        return row;
    }

    @Override
    @Transactional
    public int updateConfig(UpdateConfigParamDTO paramDTO, String sysUserId, String ipAddress) {
        CurrencyConfig config = currencyConfigMapper.selectByPrimaryKey(paramDTO.getId());
        //防空
        if (config == null) {
            throw new DataBotExeption(DataBotEnums.CURRENCY_PAIR_NULL);
        }
        //更新币对配置
        int row = currencyConfigMapper.updateConfig(paramDTO, new Date());
        //更新成功
        if (row == 1) {
            //插入更新配置操作日志记录
            currencyConfigHandleLogService.insertUpdateHandleLog(paramDTO, config, sysUserId, ipAddress);
            //查询新的对象
            CurrencyConfig afterUpdate = currencyConfigMapper.selectByPrimaryKey(paramDTO.getId());
            //将更新的配置信息放入缓存中
            setConfigListRedisHash(afterUpdate);
            //如果修改过K线发行量
            if (paramDTO.getKdayTotalAmount() != null) {
                //删除对应的K线数据Key
                removeKDAmountCache(config.getCurrencyPair());
            }
        }
        return row;
    }

    /***
     * 将新增或更新数据放到缓存中
     * @param config
     */
    private void setConfigListRedisHash(CurrencyConfig config) {
        //缓存中的key
        String redisKey = currencyConfigCache.getCurrencyConfigListKey();
        //将新增的币对配置放入缓存中
        boolean hasKey = currencyConfigCache.hasKey(redisKey);
        //判断是否存在key
        if (hasKey) {
            boolean lockFlag = false;
            String redisLockKey = currencyConfigCache.getCurrencyConfigListLockKey();
            //不断尝试获取锁
            while (!lockFlag) {
                //获取锁
                lockFlag = currencyConfigCache.tryFairLock(redisLockKey);
                //获取成功，放入缓存
                if (lockFlag) {
                    currencyConfigCache.setHashValue(redisKey, config.getCurrencyPair(), config);
                    //释放锁
                    currencyConfigCache.unFairLock(redisLockKey);
                }
            }
        } else {
            //没有对应的key，直接放入缓存中
            currencyConfigCache.setHashValue(redisKey, config.getCurrencyPair(), config);
        }
    }

    /***
     * 删除K线
     * @param currencyPair
     */
    private void removeKDAmountCache(String currencyPair) {
        //缓存key
        String redisKey = currencyConfigCache.getCurrencyKDayTotalAmountKey(currencyPair);
        //分布式锁key
        String redisLockKey = currencyConfigCache.getCurrencyKDayTotalAmountLockKey(currencyPair);
        boolean lockFlag = false;
        //不断尝试获取锁
        while (!lockFlag) {
            //获取锁
            lockFlag = currencyConfigCache.tryFairLock(redisLockKey);
            //获取成功，放入缓存
            if (lockFlag) {
                currencyConfigCache.remove(redisKey);
                //释放锁
                currencyConfigCache.unFairLock(redisLockKey);
            }
        }
    }
}
