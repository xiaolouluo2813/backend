package com.blockchain.server.base.conf;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author huangxl
 * 全局拦截器
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandle.class);
    /**
     * 处理参数异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(BaseException.class)
    public ResultDTO handleInvalidArgumentException(BaseException e) {
        return  new ResultDTO(e.getCode(),e.getMsg(),null);
    }

    /**
     * 处理rpc调用异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(RPCException.class)
    public ResultDTO handleRPCException(RPCException e) {
        return new ResultDTO(e.getCode(), e.getMsg(),null);
    }

    /**
     * 处理未知异常
     *
     * @param e 异常信息
     * @return 返回内容
     */
    @ExceptionHandler(Exception.class)
    public ResultDTO handleUnknownException(Exception e) {
        LOG.error("{}系统抛出异常，异常是：{}，异常信息是：{}", new Date(), e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        BaseException base = new BaseException();
        return new ResultDTO(base.getCode(),base.getMsg(),null);
    }
}
