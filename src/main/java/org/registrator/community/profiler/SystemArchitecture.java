package org.registrator.community.profiler;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SystemArchitecture {

    @Pointcut("execution(* org.registrator.community.controller..*.*(..))")
    public void businessController() {
    }
}