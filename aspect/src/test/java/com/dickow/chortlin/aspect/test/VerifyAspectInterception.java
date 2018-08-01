package com.dickow.chortlin.aspect.test;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyAspectInterception {

    @Test
    public void verifyInterception() {
        new InterceptedClass().interceptedMethod();
    }

    class InterceptedClass {

        @ChortlinEndpoint
        public void interceptedMethod() {
            Assertions.fail("I should be intercepted");
            System.out.println("Intercept me");
        }
    }
}


