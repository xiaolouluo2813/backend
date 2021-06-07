package com.blockchain.server.btc.common.util;

import com.blockchain.server.btc.service.IBtcWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BtcAddressSetRedisUtils {

    //redis 存储地址列表的key值，用于解析用户充值区块信息
    public static final String BTC_ADDRESS_KEY = "btc:adderss:addressSet";
    //redis 存储地址列表的时间，天数
    public static final int BTC_ADDRESS_TIME = 30;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IBtcWalletService btcWalletService;

    /**
     * 保存地址集合到redis
     *
     * @param address 新增地址
     */
    public void insert(String address) {
        if (!redisTemplate.hasKey(BTC_ADDRESS_KEY) || redisTemplate.opsForSet().size(BTC_ADDRESS_KEY) == 0) {
            Set<String> addressSet = btcWalletService.getAllWalletAddr();
            addressSet.add(address);
            redisTemplate.opsForSet().add(BTC_ADDRESS_KEY, addressSet.toArray());
        } else {
            redisTemplate.opsForSet().add(BTC_ADDRESS_KEY, address);
        }
    }

    /**
     * 从redis中获取地址集合
     *
     * @return
     */
    public Set<String> get() {
        return redisTemplate.opsForSet().members(BTC_ADDRESS_KEY);
    }

    /**
     * 判断缓存中是否存在该地址
     *
     * @param addr 地址
     * @return
     */
    public boolean isExistsAddr(String addr) {
        if (!redisTemplate.hasKey(BTC_ADDRESS_KEY) || redisTemplate.opsForSet().size(BTC_ADDRESS_KEY) == 0) {
            Set<String> addressSet = btcWalletService.getAllWalletAddr();
            if (addressSet.isEmpty()) return false;
            redisTemplate.opsForSet().add(BTC_ADDRESS_KEY, addressSet.toArray());
            return addressSet.contains(addr);
        } else {
            return redisTemplate.opsForSet().isMember(BTC_ADDRESS_KEY, addr);
        }
    }

}
