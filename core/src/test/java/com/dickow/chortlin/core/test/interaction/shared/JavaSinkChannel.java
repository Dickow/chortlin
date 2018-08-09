package com.dickow.chortlin.core.test.interaction.shared;

import com.dickow.chortlin.core.message.Channel;
import com.dickow.chortlin.core.message.IMessage;
import org.jetbrains.annotations.NotNull;

public class JavaSinkChannel<TMsg> implements Channel<TMsg> {
    @Override
    public void send(@NotNull IMessage<TMsg> message) {
        System.out.println(String.format("Dumped in the sink %s", message.getPayload()));
    }
}