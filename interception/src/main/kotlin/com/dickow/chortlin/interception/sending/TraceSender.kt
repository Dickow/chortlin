package com.dickow.chortlin.interception.sending

import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

interface TraceSender {
    fun send(invocationDTO: InvocationDTO)
    fun send(returnDTO: ReturnDTO)
}