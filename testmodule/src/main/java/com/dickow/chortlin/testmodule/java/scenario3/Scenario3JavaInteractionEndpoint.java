package com.dickow.chortlin.testmodule.java.scenario3;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;
import com.dickow.chortlin.core.message.Message;

public class Scenario3JavaInteractionEndpoint {

    @ChortlinEndpoint
    public Boolean deleteUser(Message<Integer> id) {
        throw new RuntimeException("This interaction endpoint was not intercepted");
    }
}
