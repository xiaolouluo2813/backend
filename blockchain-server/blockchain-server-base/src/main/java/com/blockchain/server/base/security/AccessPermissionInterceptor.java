package com.blockchain.server.base.security;

import com.blockchain.common.base.dto.SessionUserDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author huangxl
 * @create 2019-02-21 19:59
 */

public class AccessPermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            HandlerMethod h = (HandlerMethod)handler;
            Method method = h.getMethod();
            if(method.isAnnotationPresent(RequiresPermissions.class)){
                RequiresPermissions permissions = method.getAnnotation(RequiresPermissions.class);//拿到调用此方法需要的权限
                String value = permissions.value();
                checkPermissions(value);
            }
        }
        return true;
    }

    /**
     * 权限校验
     * @param permission
     */
    private void checkPermissions(String permission){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SessionUserDTO user = (SessionUserDTO)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority item:authorities) {
            String authority = item.getAuthority();
            if(authority.equals(permission)){
                return;
            }
        }
        throw new BaseException(BaseResultEnums.ACCESS_DENIED);
    }
}
