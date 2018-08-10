package com.dickow.chortlin.testmodule.java.scenario3;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

public class Scenario3JavaTriggerEndpoint {

    @ChortlinEndpoint
    public Integer endpoint(Integer id, String name) {
        throw new RuntimeException("This endpoint was not intercepted");
    }
}
