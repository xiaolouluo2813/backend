package com.blockchain.server.base.security.auth;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.dto.TokenDTO;
import com.blockchain.common.base.util.RSACoderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于拦截所有请求，进行解析认证
 * @author huangxl
 * @create 2019-02-18 11:59
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = checkAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    /**
     * 校验token信息，如果解析成功，交由TokenAuthenticationProvider处理
     */
    private Authentication checkAuthentication(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.SSO_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        TokenDTO tokenDTO = RSACoderUtils.decryptToken(token);
        if (tokenDTO == null) {
            return null;
        }
        SessionUserDTO user = new SessionUserDTO();
        user.setId(tokenDTO.getAccount());
        user.setTimestamp(tokenDTO.getTimestamp());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null);
        return authenticationToken;
    }
}