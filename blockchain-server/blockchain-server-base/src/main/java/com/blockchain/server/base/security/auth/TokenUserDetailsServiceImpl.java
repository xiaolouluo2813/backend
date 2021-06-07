package com.blockchain.server.base.security.auth;

import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.util.SecurityUtils;
import com.blockchain.common.base.util.SerializableUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 根据username查找相应的用户信息
 *
 * @author huangxl
 * @create 2019-02-18 13:57
 */
@Service
public class TokenUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException("can not load empty name");
        }
        SessionUserDTO user = SecurityUtils.getUserFromCache(redisTemplate, name);
        return user;
    }
}
