package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.core.handlers.IHandler1;
import org.junit.jupiter.api.Assertions;

public class Scenario2JavaInteractionHandler implements IHandler1<Integer, Long, Boolean> {
    private final Long passedId;

    public Scenario2JavaInteractionHandler(Long passedId) {
        this.passedId = passedId;
    }

    @Override
    public Long mapInput(Integer id) {
        Assertions.assertEquals(passedId.longValue(), id.longValue());
        return id.longValue();
    }

    @Override
    public Boolean process(Long input) {
        return true;
    }
}
