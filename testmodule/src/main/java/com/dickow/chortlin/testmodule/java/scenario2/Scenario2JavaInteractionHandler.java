package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.core.handlers.IHandler1;
import com.dickow.chortlin.core.message.Message;
import org.junit.jupiter.api.Assertions;

public class Scenario2JavaInteractionHandler implements IHandler1<Message<Integer>, Long, Boolean> {
    private final Long passedId;

    public Scenario2JavaInteractionHandler(Long passedId) {
        this.passedId = passedId;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public Long mapInput(Message<Integer> id) {
        Assertions.assertEquals(passedId.longValue(), id.getPayload().longValue());
        return id.getPayload().longValue();
    }

    @Override
    public Boolean process(Long input) {
        return true;
    }
}
