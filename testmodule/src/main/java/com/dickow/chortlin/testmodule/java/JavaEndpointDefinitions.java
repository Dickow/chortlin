package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.aspect.annotation.ChortlinEndpoint;

import java.util.List;

public class JavaEndpointDefinitions {

    @ChortlinEndpoint
    public Integer endpoint1() {
        throw new RuntimeException("Error I was allowed to run");
    }

    @ChortlinEndpoint
    public Integer endpointWithStringInput(String input) {
        throw new RuntimeException("Error I was allowed to run");
    }

    @ChortlinEndpoint
    public Integer endpointWith3Inputs(Integer intArg, String stringArg, List<String> listInput) {
        throw new RuntimeException("Error I was allowed to run");
    }
}
