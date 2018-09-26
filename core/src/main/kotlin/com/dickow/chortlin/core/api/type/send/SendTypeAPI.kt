package com.dickow.chortlin.core.api.type.send

import com.dickow.chortlin.core.types.AsyncSend
import com.dickow.chortlin.core.types.Participant
import com.dickow.chortlin.core.types.Path
import com.dickow.chortlin.core.types.SyncSend

class SendTypeAPI : ISendTypeAPI {
    override fun <T> syncSend(participant: Participant<T>): Path {
        return SyncSend(participant)
    }

    override fun <T> asyncSend(participant: Participant<T>): Path {
        return AsyncSend(participant)
    }
}