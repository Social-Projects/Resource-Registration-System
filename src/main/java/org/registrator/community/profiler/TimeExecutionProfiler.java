package org.registrator.community.profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeExecutionProfiler {

    @Autowired
    Logger logger;  

    @Around("org.registrator.community.profiler.SystemArchitecture.businessController()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Going to call the method: {}", pjp.getSignature().getName());
        Object output = pjp.proceed();
        logger.info("Method " + pjp.getSignature().getName() + " execution completed.");
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("Method " + pjp.getSignature().getName() + " execution time: " + elapsedTime + " milliseconds.");

        return output;
    }

/*    @After("org.registrator.community.profiler.SystemArchitecture.businessController()")
    public void profileMemory() {
        logger.info("JVM memory in use = {}", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }*/
}