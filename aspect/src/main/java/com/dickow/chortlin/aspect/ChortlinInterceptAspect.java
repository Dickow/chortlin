package com.dickow.chortlin.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ChortlinInterceptAspect {

    @Pointcut("@annotation(com.dickow.chortlin.aspect.annotation.ChortlinEndpoint)")
    public void annotationPointcut() {
    }

    @Around("annotationPointcut() && execution(* *(..))")
    public Object beforeEndpointIsCalled(ProceedingJoinPoint joinPoint) {
        System.out.println("I am intercepting the call");
        return null;
    }

}
