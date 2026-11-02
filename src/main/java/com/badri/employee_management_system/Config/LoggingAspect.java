package com.badri.employee_management_system.Config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);


    @Before("execution(* com.badri.employee_management_system.Service.Impl.*.*(..))")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {

        String className = joinPoint.getTarget().getClass().getSimpleName();

        String methodName = joinPoint.getSignature().getName();

        log.info("=========================");
        log.info("Method Execution Started");
        log.info("Class Name : {}", className);
        log.info("Method Name : {}", methodName);
        log.info("==================");
    }

    @After("execution(* com.badri.employee_management_system.Service.Impl.*.*(..))")
    public void logAfterMethodExecution(JoinPoint joinPoint) {

        String methodName = joinPoint.getSignature().getName();

        log.info("Method execution completed: {}", methodName);
    }
}
