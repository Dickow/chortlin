package com.dickow.chortlin.testmodule.java;

import com.dickow.chortlin.core.message.Channel;
import com.dickow.chortlin.core.message.Message;
import org.jetbrains.annotations.NotNull;

public class JavaSinkChannel<T> implements Channel<T> {
    @Override
    public void send(@NotNull Message<T> message) {
        System.out.println(String.format("Om nom nom %s", message.getPayload()));
    }
}
