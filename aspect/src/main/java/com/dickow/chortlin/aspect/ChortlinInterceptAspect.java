package com.dickow.chortlin.aspect;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.api.endpoint.Endpoint;
import com.dickow.chortlin.core.configuration.ChortlinConfiguration;
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
    public Object beforeEndpointIsCalled(ProceedingJoinPoint joinPoint) throws Throwable {

        Endpoint endpoint = new Endpoint(
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName());

        ChortlinConfiguration config = Chortlin.INSTANCE.lookupConfiguration(endpoint);
        if (config != null) {
            if (joinPoint.getArgs().length > 0) {
                config.applyTo(joinPoint.getArgs());
            } else {
                config.applyTo();
            }

            return null;
        } else {
            if (joinPoint.getArgs().length > 0) {
                return joinPoint.proceed(joinPoint.getArgs());
            } else {
                return joinPoint.proceed();
            }
        }
    }

}
