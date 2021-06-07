package com.blockchain.server.base.aop;

import com.blockchain.common.base.util.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class MethodAop {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.blockchain.server.*.service.*.*(..))")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        /** 获取方法名 */
        String methodName = joinPoint.getSignature().getName();
        logger.info("=================== {}方法开始执行 ===================", methodName);
        logger.info("=================== 执行的参数有:{} ===================", Arrays.toString(joinPoint.getArgs()));
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("=================== {}方法执行时使用了{}毫秒 ===================", methodName, (end - start));

        logger.info("=================== {}方法执行后返回的数据是{} ===================", JsonUtils.objectToJson(proceed));

        logger.info("=================== {}方法结束执行 ===================", methodName);
        return proceed;
    }

    @AfterThrowing(throwing = "ex", value = "aopPoint()")
    public void errorAop(JoinPoint joinPoint, Throwable ex) {
        /* 出现异常的类名 */
        String className = joinPoint.getTarget().getClass().getSimpleName();
        /*出现异常的方法名*/
        String methodName = joinPoint.getSignature().getName();
        /*传递的参数*/
        Object[] args = joinPoint.getArgs();
        /*异常类*/
        String exName = ex.getClass().getName();
        /*异常信息*/
        String exMsg = ex.getMessage();

        Object[] params = {className, methodName, exName, args, exMsg};

        logger.error("{}类的{}出现了{}异常，参数是:{}，异常信息是:{}", params);
        logger.error(exMsg, ex);
    }
}
