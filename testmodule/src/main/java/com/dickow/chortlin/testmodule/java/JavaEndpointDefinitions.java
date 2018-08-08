package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

public class JavaEndpointDefinitions {

    @ChortlinEndpoint
    public Integer endpoint1() {
        throw new RuntimeException("Error I was allowed to run");
    }
}
