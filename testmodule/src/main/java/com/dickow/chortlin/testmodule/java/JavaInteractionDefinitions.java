package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;
import com.dickow.chortlin.core.message.Message;

public class JavaInteractionDefinitions {

    @ChortlinEndpoint
    public String interactionPoint1(Message<String> input) {
        throw new RuntimeException("Error I was allowed to run");
    }
}
