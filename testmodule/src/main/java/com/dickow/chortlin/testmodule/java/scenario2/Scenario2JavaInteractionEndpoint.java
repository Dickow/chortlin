package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;
import com.dickow.chortlin.core.message.Message;

public class Scenario2JavaInteractionEndpoint {

    @ChortlinEndpoint
    public Boolean revokeRights(Message<Integer> id) {
        throw new RuntimeException("This endpoint was not intercepted");
    }
}
