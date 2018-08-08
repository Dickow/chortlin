package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.core.configuration.IChannel;
import com.dickow.chortlin.core.message.IMessage;
import org.jetbrains.annotations.NotNull;

public class JavaSinkChannel implements IChannel<String> {
    @Override
    public void send(@NotNull IMessage<String> message) {
        System.out.println(String.format("Om nom nom %s", message.getPayload()));
    }
}
