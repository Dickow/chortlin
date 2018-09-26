package com.dickow.chortlin.core.test.type;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.participant.ParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.send.ISendTypeAPI;
import com.dickow.chortlin.core.api.type.send.SendTypeAPI;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import com.dickow.chortlin.core.types.AsyncSend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsyncSendTypeAPITest {

    private final IParticipantTypeAPI participantTypeAPI = new ParticipantTypeAPI();
    private final ISendTypeAPI typeAPI = new SendTypeAPI(participantTypeAPI);

    @Test
    void createAsyncSendTypeFromClassAndMethodThatIsUnique() {
        var participant = participantTypeAPI.participant(MethodReferenceClass.class, "intReturn1InputMethod");
        typeAPI.asyncSend(participant);
    }

    @Test
    void createAsyncSendTypeWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.asyncSend(participantTypeAPI.participant(MethodReferenceClass.class, "no")));
    }

    @Test
    void createAsyncSendUsingConvenienceWrappers() {
        var send = typeAPI.asyncSend(MethodReferenceClass.class, "intReturn1InputMethod");
        assertNotNull(send);
        assertEquals(AsyncSend.class, send.getClass());
    }

    @Test
    void createAsyncSendTypeWithConvenienceWrapperWhenMethodDoesNotExist() {
        assertThrows(TypeAPIException.class,
                () -> typeAPI.asyncSend(MethodReferenceClass.class, "no"));
    }

    @Test
    void testReturnTypeForConvenienceMethods() {
        var send = typeAPI.asyncSend(MethodReferenceClass.class,
                "voidReturn2InputMethod",
                String.class, Integer.class);
        assertEquals(AsyncSend.class, send.getClass());
        send = typeAPI.asyncSend(MethodReferenceClass.class, Void.TYPE, String.class, Integer.class);
        assertEquals(AsyncSend.class, send.getClass());
    }
}
