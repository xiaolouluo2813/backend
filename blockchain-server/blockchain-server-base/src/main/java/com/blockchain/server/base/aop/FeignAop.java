package com.blockchain.server.base.aop;

import com.blockchain.common.base.dto.ResultDTO;
import com.blockchain.common.base.enums.BaseResultEnums;
import com.blockchain.common.base.exception.BaseException;
import com.blockchain.common.base.exception.RPCException;
import com.blockchain.server.base.annotation.BypassedFeign;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeignAop {
    private Logger LOG = LoggerFactory.getLogger(getClass());

    //Feign调用成功返回码
    private static final int REQUEST_SUCCESS = 200;

    /***
     * 设置切入点
     * 切所有模块的Feign包的所有方法
     */
    @Pointcut("execution(* com.blockchain.server.*.feign..*.*(..))")
    public void handleResultDTO() {
    }

    /***
     * 请求结束返回时处理
     * @param resultDTO
     */
    @AfterReturning(returning = "resultDTO", pointcut = "handleResultDTO()")
    public void doAfterReturning(JoinPoint joinPoint, ResultDTO resultDTO) {
        //通过上下文对象获取被代理对象的class
        Class<?> afterClass = joinPoint.getThis().getClass();
        //判断该对象是注解否存在
        boolean isAnno = afterClass.isAnnotationPresent(BypassedFeign.class);
        //如果存在注解，则不处理
        if (isAnno) {
            return;
        }
        //返回值为空
        if (resultDTO == null) {
            LOG.error("feign返回为空");
            throw new BaseException(BaseResultEnums.BUSY);
        }
        //RPC调用返回码不等于200，抛出异常
        if (resultDTO.getCode() != REQUEST_SUCCESS) {
            LOG.error(resultDTO.toString());
            throw new RPCException(resultDTO);
        }
    }

}
