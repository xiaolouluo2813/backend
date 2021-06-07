package com.blockchain.server.base.security.auth;

import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 对认证过程的二次处理
 * @author huangxl
 * @create 2019-03-02 11:33
 */
@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SessionUserDTO tokenDTO = (SessionUserDTO) authentication.getPrincipal();

        String account = tokenDTO.getId();
        SessionUserDTO user = (SessionUserDTO) userDetailsService.loadUserByUsername(account);
        if (user != null) {
            long timestamp = user.getTimestamp();
            if (timestamp == tokenDTO.getTimestamp()) {
                return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
            }
        }
//        SessionUserDTO user = new SessionUserDTO();
//        user.setId("test");
//        user.setUsername("test");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
