package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.core.continuation.Transform;

public class Scenario2JavaInputTransformer implements Transform<Scenario2User, Integer> {
    @Override
    public Integer transform(Scenario2User value) {
        return value.getId().intValue();
    }
}
