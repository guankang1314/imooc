package com.imooc.aspect;

import org.apache.log4j.spi.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.aspect
 * @date 2021/9/6 10:04
 */
@Component
@Aspect
@Slf4j
public class ServiceLogAspect {

    @Pointcut("execution(* com.imooc.service.impl..*.*(..))")
    public void whereCut() {

    }


    @Around("whereCut()")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("======= 开始执行 {}.{}=======",
                joinPoint.getTarget(),
                joinPoint.getSignature().getName());

        //记录开始时间
        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        //记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("==== 执行结束，耗时：{}毫秒 ====",takeTime);
        }else if (takeTime > 2000) {
            log.warn("==== 执行结束，耗时：{}毫秒 ====",takeTime);
        }else if (takeTime > 1000) {
            log.info("==== 执行结束，耗时：{}毫秒 ====",takeTime);
        }

        return result;
    }
}
