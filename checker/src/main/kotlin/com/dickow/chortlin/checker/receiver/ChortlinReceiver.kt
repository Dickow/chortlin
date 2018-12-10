package com.dickow.chortlin.checker.receiver

import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

interface ChortlinReceiver {
    fun receive(invocationDTO: InvocationDTO)
    fun receive(returnDTO: ReturnDTO)
}