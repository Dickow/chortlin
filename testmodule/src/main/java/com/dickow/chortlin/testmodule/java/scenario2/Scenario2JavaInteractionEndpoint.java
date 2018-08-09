package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

public class Scenario2JavaInteractionEndpoint {

    @ChortlinEndpoint
    public Boolean revokeRights(Integer id) {
        throw new RuntimeException("This endpoint was not intercepted");
    }
}
