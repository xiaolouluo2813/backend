package com.blockchain.server.cct.service.impl;

import com.blockchain.server.cct.common.enums.CCTEnums;
import com.blockchain.server.cct.common.exception.CCTException;
import com.blockchain.server.cct.common.util.RandomListUtil;
import com.blockchain.server.cct.dto.automaticdata.AutomaticdataDTO;
import com.blockchain.server.cct.entity.Automaticdata;
import com.blockchain.server.cct.mapper.AutomaticdataMapper;
import com.blockchain.server.cct.service.AutomaticdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AutomaticdataServiceImpl implements AutomaticdataService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AutomaticdataMapper automaticdataMapper;

    @Override
    @Transactional
    public int insertAutomaticdata(Automaticdata param) {
        Automaticdata automaticdata = automaticdataMapper.selectByCoinAndUnitAndType(param.getCoinName(), param.getUnitName(), param.getOrderType());
        //判断是否已存在
        if (automaticdata != null) {
            throw new CCTException(CCTEnums.AUTOMATICDATA_NOT_NULL);
        }
        //1.生成两个key，买/卖对应的单价和数量key
        //交易对对应的key
        String key = generateKey(param.getCoinName(), param.getUnitName(), param.getOrderType());
        //2.根据范围生成盘口数据
        List<AutomaticdataDTO> automaticdatas = RandomListUtil.addRandomList(param);
        //4.插入redis
        redisTemplate.opsForList().leftPushAll(key, automaticdatas);
        param.setId(UUID.randomUUID().toString());
        return automaticdataMapper.insertSelective(param);
    }

    @Override
    public List<Automaticdata> listAll() {
        return automaticdataMapper.selectAll();
    }

    @Override
    @Transactional
    public int deleteAutomaticdata(String id) {
        Automaticdata automaticdata = automaticdataMapper.selectByPrimaryKey(id);
        if (automaticdata == null) {
            return 0;
        }
        //删除redis中的key
        String key = generateKey(automaticdata.getCoinName(), automaticdata.getUnitName(), automaticdata.getOrderType());
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        return automaticdataMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updataAutomaticdata(Automaticdata param) {
        Automaticdata automaticdata = automaticdataMapper.selectByCoinAndUnitAndType(param.getCoinName(), param.getUnitName(), param.getOrderType());
        //判断是否已存在
        if (automaticdata != null && !automaticdata.getId().equals(param.getId())) {
            throw new CCTException(CCTEnums.AUTOMATICDATA_NOT_NULL);
        }
        //更新数据库盘口规则
        int updateRow = automaticdataMapper.updateByPrimaryKeySelective(param);
        //生成对应的key
        String key = generateKey(param.getCoinName(), param.getUnitName(), param.getOrderType());
        //根据范围生成盘口数据
        List<AutomaticdataDTO> automaticdatas = RandomListUtil.addRandomList(param);
        //将更新后的规则生成的盘口数据放进redis中
        redisTemplate.opsForList().leftPushAll(key, automaticdatas);
        //如果数据量大于20条
        if (redisTemplate.opsForList().size(key) > 20) {
            //将20条以后的数据去除
            while (true) {
                redisTemplate.opsForList().rightPop(key);
                if (redisTemplate.opsForList().size(key) <= 20) {
                    break;
                }
            }
        }
        return updateRow;
    }

    /***
     * 构建redisKey
     * @param coinName
     * @param unitName
     * @param orderType
     * @return
     */
    private String generateKey(String coinName, String unitName, String orderType) {
        return "cct:" + coinName + "/" + unitName + ":" + orderType;
    }
}
