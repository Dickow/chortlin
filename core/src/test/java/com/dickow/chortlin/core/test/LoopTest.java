package com.dickow.chortlin.core.test;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.choreography.Choreography;
import com.dickow.chortlin.core.types.choreography.PathChoreography;
import com.dickow.chortlin.core.types.path.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

class LoopTest extends BasePathTest {

    @Test
    void testSimplestLoop() {
        var chor = api.start(MethodReferenceClass.class, "intReturn1InputMethod")
                .loop(api.syncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                .finish();

        Assertions.assertEquals(PathChoreography.class, chor.getClass());
        var loop = ((PathChoreography) chor).getPath();
        Assertions.assertEquals(Loop.class, loop.getClass());
        var leftPath = ((Loop) loop).getLeftPath();
        var rightPath = ((Loop) loop).getRightPath();
        Assertions.assertEquals(Start.class, leftPath.getClass());
        Assertions.assertEquals(SyncSend.class, rightPath.getClass());
    }

    @Test
    void ensureExceptionIsThrownForUnknownParticipant() {
        Supplier<Choreography> chor = () -> api.start(MethodReferenceClass.class, "intReturn1InputMethod")
                .loop(api.syncSend(MethodReferenceClass.class, "notFound"))
                .finish();
        Assertions.assertThrows(TypeAPIException.class, chor::get);
    }

    @Test
    void checkThatLoopsCanBeCombined() {
        var chor = api.start(MethodReferenceClass.class, "intReturn1InputMethod")
                .loop(api.syncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                .sequence(api.asyncSend(MethodReferenceClass.class, "stringReturnMethod"))
                .finish();

        Assertions.assertEquals(PathChoreography.class, chor.getClass());
        var sequence = ((PathChoreography) chor).getPath();
        Assertions.assertEquals(Sequence.class, sequence.getClass());
        var loop = ((Sequence) sequence).getLeftPath();
        var asynSend = ((Sequence) sequence).getRightPath();
        Assertions.assertEquals(Loop.class, loop.getClass());
        var leftPath = ((Loop) loop).getLeftPath();
        var rightPath = ((Loop) loop).getRightPath();
        Assertions.assertEquals(Start.class, leftPath.getClass());
        Assertions.assertEquals(SyncSend.class, rightPath.getClass());
        Assertions.assertEquals(AsyncSend.class, asynSend.getClass());
    }
}
