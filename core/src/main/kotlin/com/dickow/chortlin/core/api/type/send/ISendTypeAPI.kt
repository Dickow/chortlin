package com.dickow.chortlin.core.api.type.send

import com.dickow.chortlin.core.ast.participant.Participant
import com.dickow.chortlin.core.types.path.Path

interface ISendTypeAPI {
    fun <T> asyncSend(participant: Participant<T>): Path
    // Convenience methods for creating async send types
    fun <T> asyncSend(clazz: Class<T>, method: String): Path

    fun <T> asyncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path
    fun <T> asyncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path

    fun <T> syncSend(participant: Participant<T>): Path
    // Convenience methods for creating sync send types
    fun <T> syncSend(clazz: Class<T>, method: String): Path

    fun <T> syncSend(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Path
    fun <T> syncSend(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Path
}