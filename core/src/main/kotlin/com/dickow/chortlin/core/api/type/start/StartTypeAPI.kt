package com.dickow.chortlin.core.api.type.start

import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI
import com.dickow.chortlin.core.ast.participant.Participant
import com.dickow.chortlin.core.types.path.Start

class StartTypeAPI(private val participantTypeAPI: IParticipantTypeAPI) : IStartTypeAPI {

    override fun <T> start(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Start<T> {
        return start(participantTypeAPI.participant(clazz, returnType, *paramTypes))
    }

    override fun <T> start(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Start<T> {
        return start(participantTypeAPI.participant(clazz, methodName, *paramTypes))
    }

    override fun <T> start(clazz: Class<T>, method: String): Start<T> {
        return start(participantTypeAPI.participant(clazz, method))
    }

    override fun <T> start(participant: Participant<T>): Start<T> {
        return Start(participant)
    }
}