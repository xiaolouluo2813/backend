package com.blockchain.server.sysconf.common.exception;

import com.blockchain.common.base.exception.BaseException;
import com.blockchain.server.sysconf.common.enums.SysconfResultEnums;
import lombok.Data;

/**
 * 自定义基础异常，用于向上跑出可预知异常
 *
 * @author huangxl
 * @create 2018-11-12 11:09
 */
@Data
public class SysconfException extends BaseException {
    protected int code;
    protected String msg;

    public SysconfException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SysconfException(SysconfResultEnums rs) {
        this.code = rs.getCode();
        this.msg = rs.getMsg();
    }
}