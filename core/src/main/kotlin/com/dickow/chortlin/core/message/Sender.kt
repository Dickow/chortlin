package com.dickow.chortlin.core.message

import com.dickow.chortlin.core.continuation.Accumulator

interface Sender {
    fun send(msg: Any?, accumulator: Accumulator)
}