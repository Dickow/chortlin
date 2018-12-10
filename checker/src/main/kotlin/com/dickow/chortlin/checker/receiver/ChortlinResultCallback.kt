package com.dickow.chortlin.checker.receiver

import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

interface ChortlinResultCallback {
    fun error(invocationDTO: InvocationDTO)
    fun error(returnDTO: ReturnDTO)
}