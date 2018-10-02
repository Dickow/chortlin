package com.dickow.chortlin.core.test;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.choreography.Choreography;
import com.dickow.chortlin.core.types.choreography.FlowChoreography;
import com.dickow.chortlin.core.types.flow.Fork;
import com.dickow.chortlin.core.types.path.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class ForkTest extends BasePathTest {

    @Test
    void createSimpleFork() {
        var chor = api.start(MethodReferenceClass.class, "stringReturnMethod")
                .fork((o -> o.sequence(api.asyncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                                .optional(api.asyncSend(MethodReferenceClass.class, Void.TYPE, String.class))),
                        (o) -> o.optional(api.syncSend(MethodReferenceClass.class, "intReturn1InputMethod")))
                .finish();

        Assertions.assertEquals(FlowChoreography.class, chor.getClass());
        var flow = ((FlowChoreography) chor).getFlow();
        Assertions.assertEquals(Fork.class, flow.getClass());
        var leftPath = ((Fork) flow).getLeftPath();
        var rightPaths = ((Fork) flow).getRightPaths();
        Assertions.assertEquals(Start.class, leftPath.getClass());
        Assertions.assertEquals(2, rightPaths.size(), "Expected exactly 2 paths");
        Assertions.assertEquals(Optional.class, rightPaths.get(0).getClass());
        Assertions.assertEquals(Optional.class, rightPaths.get(1).getClass());

        var fork1Option = ((Optional) rightPaths.get(0));
        Assertions.assertEquals(Sequence.class, fork1Option.getLeftPath().getClass());
        Assertions.assertEquals(AsyncSend.class, fork1Option.getRightPath().getClass());
        var fork1OptionSequence = ((Sequence) fork1Option.getLeftPath());
        Assertions.assertEquals(Start.class, fork1OptionSequence.getLeftPath().getClass());
        Assertions.assertEquals(AsyncSend.class, fork1OptionSequence.getRightPath().getClass());

        var fork2Option = ((Optional) rightPaths.get(1));
        Assertions.assertEquals(Start.class, fork2Option.getLeftPath().getClass());
        Assertions.assertEquals(SyncSend.class, fork2Option.getRightPath().getClass());
    }

    @Test
    void ensureExceptionIsThrownForParticipantException() {
        Assertions.assertThrows(
                TypeAPIException.class,
                () -> api.start(MethodReferenceClass.class, "stringReturnMethod")
                        .fork((o -> o.sequence(api.asyncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                                        .optional(api.asyncSend(MethodReferenceClass.class, "duplicateMethod"))),
                                (o) -> o.optional(api.syncSend(MethodReferenceClass.class, "intReturn1InputMethod")))
                        .finish());
    }

    @Test
    void ensureMoreThanOnePathForFork() {
        Supplier<Choreography> config = () -> api
                .start(MethodReferenceClass.class, "stringReturnMethod")
                .fork((o) -> o.optional(api.syncSend(MethodReferenceClass.class, "intReturn1InputMethod")))
                .finish();
        Assertions.assertThrows(TypeAPIException.class, config::get);
    }

    @Test
    void ensureErrorOnZeroArgumentsForFork() {
        Supplier<Choreography> config = () -> api
                .start(MethodReferenceClass.class, "stringReturnMethod")
                .fork()
                .finish();
        Assertions.assertThrows(TypeAPIException.class, config::get);
    }
}
