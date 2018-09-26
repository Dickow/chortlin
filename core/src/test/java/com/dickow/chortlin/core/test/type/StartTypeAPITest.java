package com.dickow.chortlin.core.test.type;

import com.dickow.chortlin.core.api.exceptions.TypeAPIException;
import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.participant.ParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.start.IStartTypeAPI;
import com.dickow.chortlin.core.api.type.start.StartTypeAPI;
import com.dickow.chortlin.core.test.shared.MethodReferenceClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class StartTypeAPITest {

    private final IStartTypeAPI typeAPI = new StartTypeAPI();
    private final IParticipantTypeAPI participantTypeAPI = new ParticipantTypeAPI();

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
}
