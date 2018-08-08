package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

public class JavaInteractionDefinitions {

    @ChortlinEndpoint
    public String interactionPoint1(String input) {
        throw new RuntimeException("Error I was allowed to run");
    }
}
