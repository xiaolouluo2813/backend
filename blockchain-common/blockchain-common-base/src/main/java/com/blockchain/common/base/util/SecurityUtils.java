package com.blockchain.common.base.util;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.SessionUserDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.TimeUnit;

/**
 * @author huangxl
 * @create 2019-03-06 09:54
 */
public class SecurityUtils {

    /**
     * 获取用户信息
     */
    public static SessionUserDTO  getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }
        if(authentication == null){
            return null;
        }
        return (SessionUserDTO) authentication.getPrincipal();
    }

    /**
     * 获取用户id
     */
    public static String getUserId() {
        SessionUserDTO user = getUser();
        return user.getId();
    }

    /**
     * 获取用户名
     */
    public static String getUsername() {
        SessionUserDTO user = getUser();
        return user.getUsername();
    }

    /**
     * 将登录信息设置到缓存
     *
     * @param user          用户信息
     * @param redisTemplate redis操作对象
     */
    public static void setUser(SessionUserDTO user, RedisTemplate redisTemplate) {
        redisTemplate.opsForValue().set(BaseConstant.REDIS_TOKEN_KEY + user.getUsername(), SerializableUtil.serializeToString(user), BaseConstant.REDIS_TOKEN_KEY_EXPIRE, TimeUnit.MINUTES);
    }

    /**
     * 从缓存中获取用户信息
     */
    public static SessionUserDTO getUserFromCache(RedisTemplate redisTemplate, String username) {
        String str = (String) redisTemplate.opsForValue().get(BaseConstant.REDIS_TOKEN_KEY + username);
        Object obj = null;
        if (str != null) {
            obj = SerializableUtil.unserializeStringToObject(str);
        }
        SessionUserDTO user = (SessionUserDTO) obj;
        if (user != null) {
            resetExpire(user, redisTemplate);
        }
        return user;
    }

    /**
     * 重置超时时间
     */
    private static void resetExpire(SessionUserDTO user, RedisTemplate redisTemplate) {
        setUser(user, redisTemplate);
    }

    /**
     * 删除缓存信息
     *
     * @param redisTemplate redis操作对象
     */
    public static void removeUser(RedisTemplate redisTemplate) {
        SessionUserDTO user = getUser();
        if (user == null) {
            return;
        }
        SessionUserDTO cacheUser = getUserFromCache(redisTemplate, user.getUsername());
        if (cacheUser != null && cacheUser.getTimestamp().equals(user.getTimestamp())) {
            redisTemplate.delete(BaseConstant.REDIS_TOKEN_KEY + user.getUsername());
        }
    }

}
