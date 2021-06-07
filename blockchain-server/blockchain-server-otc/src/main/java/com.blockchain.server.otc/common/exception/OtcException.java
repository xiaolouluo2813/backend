package com.blockchain.server.otc.common.exception;

import com.blockchain.common.base.constant.BaseConstant;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.util.HttpRequestUtil;
import com.blockchain.server.otc.common.enums.OtcEnums;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class OtcException extends BaseException {
    public OtcException(OtcEnums rs) {
        this.code = rs.getCode();
        this.msg = rs.getMsg();
    }
}
