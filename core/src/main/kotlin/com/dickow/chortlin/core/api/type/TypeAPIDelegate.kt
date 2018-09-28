package com.dickow.chortlin.core.api.type

import com.dickow.chortlin.core.api.type.participant.IParticipantTypeAPI
import com.dickow.chortlin.core.api.type.send.ISendTypeAPI
import com.dickow.chortlin.core.api.type.start.IStartTypeAPI
import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.path.Path
import com.dickow.chortlin.core.types.path.Start

class TypeAPIDelegate constructor(
        private val participantTypeAPI: IParticipantTypeAPI,
        private val startTypeAPI: IStartTypeAPI,
        private val sendTypeAPI: ISendTypeAPI) : TypeAPI {

    override fun <T> asyncSend(clazz: Class<T>, method: String): Path {
        return sendTypeAPI.asyncSend(clazz, method)
    }

    override fun <T> asyncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path {
        return sendTypeAPI.asyncSend(clazz, returnType, *paramTypes)
    }

    override fun <T> asyncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path {
        return sendTypeAPI.asyncSend(clazz, methodName, *paramTypes)
    }

    override fun <T> syncSend(clazz: Class<T>, method: String): Path {
        return sendTypeAPI.syncSend(clazz, method)
    }

    override fun <T> syncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path {
        return sendTypeAPI.syncSend(clazz, returnType, *paramTypes)
    }

    override fun <T> syncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path {
        return sendTypeAPI.syncSend(clazz, methodName, *paramTypes)
    }

    override fun <T> start(clazz: Class<T>, method: String): Start<T> {
        return startTypeAPI.start(clazz, method)
    }

    override fun <T> start(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Start<T> {
        return startTypeAPI.start(clazz, returnType, *paramTypes)
    }

    override fun <T> start(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Start<T> {
        return startTypeAPI.start(clazz, methodName, *paramTypes)
    }

    override fun <T> asyncSend(participant: Participant<T>): Path {
        return sendTypeAPI.asyncSend(participant)
    }

    override fun <T> syncSend(participant: Participant<T>): Path {
        return sendTypeAPI.syncSend(participant)
    }

    override fun <T> start(participant: Participant<T>): Start<T> {
        return startTypeAPI.start(participant)
    }

    override fun <T> participant(clazz: Class<T>, method: String): Participant<T> {
        return participantTypeAPI.participant(clazz, method)
    }

    override fun <T> participant(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Participant<T> {
        return participantTypeAPI.participant(clazz, returnType, *paramTypes)
    }

    override fun <T> participant(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Participant<T> {
        return participantTypeAPI.participant(clazz, methodName, *paramTypes)
    }
}