package com.dickow.chortlin.core.message

import java.time.LocalDateTime

class Message<T> {
    var payload: T? = null
    var hashes: Collection<Int> = emptyList()
    val timeStamp = LocalDateTime.now()
}