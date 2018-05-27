package com.mksoft.shop.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 记录所有controller和service等Component的调用log
 * Created by oneway on 2017/4/25.
 */
@Aspect
@Component
public class SpringLog {

    // 本地异常日志记录对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@Pointcut("@target(org.springframework.web.bind.annotation.RestController)")
    @Pointcut("within(com.mksoft.shop.controller..*)")
    public void allController() {}

    //@Pointcut("@target(org.springframework.stereotype.Service)")
    @Pointcut("within(com.mksoft.shop.service.impl..*)")
    public void allService() {}

    @Pointcut("allController() || allService()")
    public void allControllerAndService() {}

    // 前置通知用于拦截service层的日志
    @Before("allControllerAndService()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // 获取包括包名在内的全方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.debug(String.format("%s.%s() >>", className, methodName));
    }

    // 前置通知用于拦截service层的日志
    @After("allControllerAndService()")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        // 获取包括包名在内的全方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        logger.debug(String.format("%s.%s() <<", className, methodName));
    }

}

