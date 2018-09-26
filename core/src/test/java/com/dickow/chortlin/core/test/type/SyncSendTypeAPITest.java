package com.dickow.chortlin.core.test.type;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.participant.ParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.send.ISendTypeAPI;
import com.dickow.chortlin.core.api.type.send.SendTypeAPI;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.SyncSend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SyncSendTypeAPITest {

    private final IParticipantTypeAPI participantTypeAPI = new ParticipantTypeAPI();
    private final ISendTypeAPI typeAPI = new SendTypeAPI(participantTypeAPI);

    @Test
    void createAsyncSendTypeFromClassAndMethodThatIsUnique() {
        var participant = participantTypeAPI.participant(MethodReferenceClass.class, "intReturn1InputMethod");
        typeAPI.syncSend(participant);
    }

    @Test
    void createAsyncSendTypeWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.syncSend(participantTypeAPI.participant(MethodReferenceClass.class, "no")));
    }

    @Test
    void createAsyncSendUsingConvenienceWrappers() {
        var start = typeAPI.syncSend(MethodReferenceClass.class, "intReturn1InputMethod");
        assertNotNull(start);
    }

    @Test
    void createAsyncSendTypeWithConvenienceWrapperWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.syncSend(MethodReferenceClass.class, "no"));
    }

    @Test
    void testReturnTypeForConvenienceMethods() {
        var send = typeAPI.syncSend(MethodReferenceClass.class,
                "voidReturn2InputMethod",
                String.class, Integer.class);
        assertEquals(SyncSend.class, send.getClass());
        send = typeAPI.syncSend(MethodReferenceClass.class, Void.TYPE, String.class, Integer.class);
        assertEquals(SyncSend.class, send.getClass());
    }
}
