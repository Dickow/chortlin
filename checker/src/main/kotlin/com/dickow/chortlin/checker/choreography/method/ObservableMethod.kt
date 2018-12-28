package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import java.lang.reflect.Method

data class ObservableMethod(val method: String, val participant: InternalParticipant)
