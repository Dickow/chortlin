package com.dickow.chortlin.core.api.type.send

import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.Path

interface ISendTypeAPI {
    fun <T> asyncSend(participant: Participant<T>): Path
    fun <T> syncSend(participant: Participant<T>): Path
}