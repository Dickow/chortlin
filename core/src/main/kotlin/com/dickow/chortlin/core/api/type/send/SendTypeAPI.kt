package com.dickow.chortlin.core.api.type.send

import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI
import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.path.AsyncSend
import com.dickow.chortlin.core.types.path.Path
import com.dickow.chortlin.core.types.path.SyncSend

class SendTypeAPI(private val participantTypeAPI: IParticipantTypeAPI) : ISendTypeAPI {
    override fun <T> asyncSend(clazz: Class<T>, method: String): Path {
        return AsyncSend(participantTypeAPI.participant(clazz, method))
    }

    override fun <T> asyncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path {
        return AsyncSend(participantTypeAPI.participant(clazz, returnType, *paramTypes))
    }

    override fun <T> asyncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path {
        return AsyncSend(participantTypeAPI.participant(clazz, methodName, *paramTypes))
    }

    override fun <T> syncSend(clazz: Class<T>, method: String): Path {
        return SyncSend(participantTypeAPI.participant(clazz, method))
    }

    override fun <T> syncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path {
        return SyncSend(participantTypeAPI.participant(clazz, returnType, *paramTypes))
    }

    override fun <T> syncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path {
        return SyncSend(participantTypeAPI.participant(clazz, methodName, *paramTypes))
    }

    override fun <T> syncSend(participant: Participant<T>): Path {
        return SyncSend(participant)
    }

    override fun <T> asyncSend(participant: Participant<T>): Path {
        return AsyncSend(participant)
    }
}