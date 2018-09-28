package com.dickow.chortlin.core.api.type.start

import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.path.Start

interface IStartTypeAPI {
    fun <T> start(participant: Participant<T>): Start<T>

    // Wrappers to avoid using the participant api for creating participants.
    fun <T> start(clazz: Class<T>, method: String): Start<T>

    fun <T> start(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Start<T>
    fun <T> start(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Start<T>
}