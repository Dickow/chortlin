package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

public class Scenario2JavaTriggerEndpoint {

    @ChortlinEndpoint
    public Integer reduceRightsForUser(Long id, String name, Boolean isAdmin) {
        throw new RuntimeException("This endpoint should have been intercepted");
    }
}
