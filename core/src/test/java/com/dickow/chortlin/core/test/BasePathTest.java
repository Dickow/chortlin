package com.dickow.chortlin.core.test;

import com.dickow.chortlin.core.api.type.TypeAPI;
import com.dickow.chortlin.core.api.type.TypeAPIDelegate;
import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.participant.ParticipantTypeAPI;
import com.dickow.chortlin.core.api.type.send.SendTypeAPI;
import com.dickow.chortlin.core.api.type.start.StartTypeAPI;

abstract class BasePathTest {
    private final IParticipantTypeAPI participantApi = new ParticipantTypeAPI();
    final TypeAPI api =
            new TypeAPIDelegate(participantApi, new StartTypeAPI(participantApi), new SendTypeAPI(participantApi));
}
