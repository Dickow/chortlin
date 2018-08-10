package com.dickow.chortlin.aspect;

import com.dickow.chortlin.core.Chortlin;
import com.dickow.chortlin.core.api.endpoint.Endpoint;
import com.dickow.chortlin.core.configuration.ChortlinConfiguration;
import com.dickow.chortlin.core.continuation.Accumulator;
import com.dickow.chortlin.core.message.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Optional;

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
        ChortlinConfiguration config;
        Accumulator accumulator = new Accumulator(endpoint);

        if (methodIsAnInteraction(joinPoint)) {
            Message msg = (Message) joinPoint.getArgs()[0];
            config = tryToGetInteractionConfiguration(msg, endpoint);
            accumulator.setHashes(msg.getHashes());
        } else {
            config = Chortlin.get().lookupConfiguration(endpoint, endpoint);
        }

        if (config != null) {
            if (joinPoint.getArgs().length > 0) {
                config.applyTo(joinPoint.getArgs(), accumulator);
            } else {
                config.applyTo(accumulator);
            }

            return null;
        } else {
            return joinPoint.getArgs().length > 0
                    ? joinPoint.proceed(joinPoint.getArgs())
                    : joinPoint.proceed();
        }
    }

    private ChortlinConfiguration tryToGetInteractionConfiguration(Message msg, Endpoint endpoint) {
        Optional<Integer> rootHash = msg.getHashes().stream().findFirst();
        return rootHash.map(integer -> Chortlin.get().lookupConfiguration(integer, endpoint)).orElse(null);
    }

    private boolean methodIsAnInteraction(ProceedingJoinPoint joinPoint) {
        return joinPoint.getArgs().length == 1 && (joinPoint.getArgs()[0] instanceof Message);
    }

}
