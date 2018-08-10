package com.dickow.chortlin.testmodule.java.scenario3;

import com.dickow.chortlin.core.handlers.IHandler1;
import com.dickow.chortlin.core.message.Message;

public class Scenario3JavaInteractionHandler implements IHandler1<Message<Integer>, Integer, Boolean> {
    @Override
    public Integer mapInput(Message<Integer> arg) {
        return null;
    }

    @Override
    public Boolean process(Integer input) {
        return null;
    }
}
