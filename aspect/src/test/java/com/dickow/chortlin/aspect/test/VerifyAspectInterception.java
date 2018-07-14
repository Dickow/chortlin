package com.dickow.chortlin.aspect.test;

import com.dickow.chortlin.aspect.annotation.TestAnnotation;
import org.junit.jupiter.api.Test;

public class VerifyAspectInterception {

    @Test
    public void verifyInterception() {
        new InterceptedClass().interceptedMethod();
    }

    class InterceptedClass {
        @TestAnnotation
        public void interceptedMethod() {
            System.out.println("Intercept me");
        }
    }
}


