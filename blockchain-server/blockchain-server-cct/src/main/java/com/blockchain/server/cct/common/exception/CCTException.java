package com.blockchain.server.cct.common.exception;

import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.cct.common.enums.CCTEnums;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class CCTException extends BaseException {
    public CCTException(CCTEnums rs) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //可能是定时器调用，避免获取request空指针
        if (servletRequestAttributes == null) {
            this.code = rs.getCode();
            this.msg = rs.getMsg();
        } else {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            this.code = rs.getCode();
            this.msg = rs.getMsg();
        }
    }
}
