package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant

object MethodFactory {
    fun method(participant: InternalParticipant, methodName: String): ObservableMethod {
        return ObservableMethod(methodName, participant)
    }
}