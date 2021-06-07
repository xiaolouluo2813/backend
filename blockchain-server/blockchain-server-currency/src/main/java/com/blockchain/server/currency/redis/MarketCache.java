package com.blockchain.server.currency.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * \* <p>Desciption:</p>
 * \* CreateTime: 2019/2/12 10:08
 * \* User: XianChaoWei
 * \* Version: V1.0
 * \
 */
@Component
public class MarketCache {
    private static final String MARKET_LIST_LIST_CACHE = "market:list:list";
    private static final String MARKET_LIST_HOME_CACHE = "market:list:home";

    @Autowired
    private RedisTemplate redisTemplate;

    public void deleteHomeList() {
        redisTemplate.delete(MARKET_LIST_HOME_CACHE);
    }

    public void deleteList() {
        redisTemplate.delete(MARKET_LIST_LIST_CACHE);
    }
}
