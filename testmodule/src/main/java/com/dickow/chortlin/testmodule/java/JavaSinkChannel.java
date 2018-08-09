package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.core.message.Channel;
import com.dickow.chortlin.core.message.IMessage;
import org.jetbrains.annotations.NotNull;

public class JavaSinkChannel implements Channel<String> {
    @Override
    public void send(@NotNull IMessage<String> message) {
        System.out.println(String.format("Om nom nom %s", message.getPayload()));
    }
}
