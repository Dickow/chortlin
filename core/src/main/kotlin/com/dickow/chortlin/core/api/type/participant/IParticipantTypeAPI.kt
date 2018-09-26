package com.dickow.chortlin.core.api.type.participant

import com.dickow.chortlin.core.types.Participant

interface IParticipantTypeAPI {
    fun <T> participant(clazz: Class<T>, method: String): Participant<T>
    fun <T> participant(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Participant<T>
    fun <T> participant(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Participant<T>
}