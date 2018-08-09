package com.dickow.chortlin.testmodule.java.scenario2;

import com.dickow.chortlin.core.handlers.IHandler3;

public class Scenario2JavaTriggerHandler implements IHandler3<Long, String, Boolean, Scenario2User, Scenario2User> {
    @Override
    public Scenario2User mapInput(Long id, String name, Boolean isAdmin) {
        return new Scenario2User(id, name, isAdmin);
    }

    @Override
    public Scenario2User process(Scenario2User input) {
        return input;
    }
}
