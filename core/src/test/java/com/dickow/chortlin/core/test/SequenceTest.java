package com.dickow.chortlin.core.test;

import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.choreography.PathChoreography;
import com.dickow.chortlin.core.types.path.AsyncSend;
import com.dickow.chortlin.core.types.path.Sequence;
import com.dickow.chortlin.core.types.path.Start;
import com.dickow.chortlin.core.types.path.SyncSend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SequenceTest extends BasePathTest {

    @Test
    void createSimpleSequence() {
        var choreography = api
                .start(MethodReferenceClass.class, "intReturn1InputMethod")
                .sequence(api.asyncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                .finish();

        Assertions.assertEquals(PathChoreography.class, choreography.getClass());
        var path = ((PathChoreography) choreography).getPath();
        Assertions.assertEquals(Sequence.class, path.getClass());
        var leftPath = ((Sequence) path).getLeftPath();
        var rightPath = ((Sequence) path).getRightPath();
        Assertions.assertEquals(Start.class, leftPath.getClass());
        Assertions.assertEquals(AsyncSend.class, rightPath.getClass());
    }

    @Test
    void createSequenceFromStartToSyncSend() {
        var choreography = api
                .start(MethodReferenceClass.class, "intReturn1InputMethod")
                .sequence(api.syncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                .finish();

        Assertions.assertEquals(PathChoreography.class, choreography.getClass());
        var path = ((PathChoreography) choreography).getPath();
        Assertions.assertEquals(Sequence.class, path.getClass());
        var leftPath = ((Sequence) path).getLeftPath();
        var rightPath = ((Sequence) path).getRightPath();
        Assertions.assertEquals(Start.class, leftPath.getClass());
        Assertions.assertEquals(SyncSend.class, rightPath.getClass());
    }

    @Test
    void createStandaloneSequence() {
        var sequence = api
                .start(MethodReferenceClass.class, "intReturn1InputMethod")
                .sequence(api.syncSend(MethodReferenceClass.class, "voidReturn2InputMethod"));
        Assertions.assertEquals(Sequence.class, sequence.getClass());
        var start = ((Sequence) sequence).getLeftPath();
        var send = ((Sequence) sequence).getRightPath();
        Assertions.assertEquals(Start.class, start.getClass());
        Assertions.assertEquals(SyncSend.class, send.getClass());
    }

    @Test
    void createSequenceFromStartToSyncToAsync() {
        var choreography = api
                .start(MethodReferenceClass.class, "intReturn1InputMethod")
                .sequence(api.syncSend(MethodReferenceClass.class, "voidReturn2InputMethod"))
                .sequence(api.asyncSend(MethodReferenceClass.class, "stringReturnMethod"))
                .finish();

        Assertions.assertEquals(PathChoreography.class, choreography.getClass());
        var path = ((PathChoreography) choreography).getPath();
        Assertions.assertEquals(Sequence.class, path.getClass());
        var leftPath = ((Sequence) path).getLeftPath();
        var rightPath = ((Sequence) path).getRightPath();
        Assertions.assertEquals(Sequence.class, leftPath.getClass());
        Assertions.assertEquals(AsyncSend.class, rightPath.getClass());
        var leftLeftPath = ((Sequence) leftPath).getLeftPath();
        var leftRightPath = ((Sequence) leftPath).getRightPath();
        Assertions.assertEquals(Start.class, leftLeftPath.getClass());
        Assertions.assertEquals(SyncSend.class, leftRightPath.getClass());
    }
}
