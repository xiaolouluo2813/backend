package com.blockchain.server.system.common.exception;

import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.system.common.enums.SystemResultEnums;
import lombok.Data;

/**
 * 自定义基础异常，用于向上跑出可预知异常
 *
 * @author huangxl
 * @create 2018-11-12 11:09
 */
@Data
public class SystemException extends BaseException {
    protected int code;
    protected String msg;

    public SystemException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SystemException(SystemResultEnums rs) {
        this.code = rs.getCode();
        this.msg = rs.getMsg();
    }
}