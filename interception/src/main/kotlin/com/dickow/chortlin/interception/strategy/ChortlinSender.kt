package com.dickow.chortlin.interception.strategy

import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

interface ChortlinSender {
    fun send(invocationDTO: InvocationDTO)
    fun send(returnDTO: ReturnDTO)
}