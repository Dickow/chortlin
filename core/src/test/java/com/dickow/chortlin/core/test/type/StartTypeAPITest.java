package com.dickow.chortlin.core.test.type;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.participant.ParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.start.IStartTypeAPI;
import com.dickow.chortlin.core.api.type.start.StartTypeAPI;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.path.Start;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartTypeAPITest {

    private final IParticipantTypeAPI participantTypeAPI = new ParticipantTypeAPI();
    private final IStartTypeAPI typeAPI = new StartTypeAPI(participantTypeAPI);

    @Test
    void createStartTypeFromClassAndMethodThatIsUnique() {
        var participant = participantTypeAPI.participant(MethodReferenceClass.class, "intReturn1InputMethod");
        typeAPI.start(participant);
    }

    @Test
    void createStartTypeWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.start(participantTypeAPI.participant(MethodReferenceClass.class, "no")));
    }

    @Test
    void createStartUsingConvenienceWrappers() {
        var start = typeAPI.start(MethodReferenceClass.class, "intReturn1InputMethod");
        assertNotNull(start);
    }

    @Test
    void createStartTypeWithConvenienceWrapperWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.start(MethodReferenceClass.class, "no"));
    }

    @Test
    void testReturnTypeForConvenienceMethods() {
        var send = typeAPI.start(MethodReferenceClass.class,
                "voidReturn2InputMethod",
                String.class, Integer.class);
        assertEquals(Start.class, send.getClass());
        send = typeAPI.start(MethodReferenceClass.class, Void.TYPE, String.class, Integer.class);
        assertEquals(Start.class, send.getClass());
    }
}
