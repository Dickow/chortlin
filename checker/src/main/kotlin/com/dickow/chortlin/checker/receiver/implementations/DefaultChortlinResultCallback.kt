package com.dickow.chortlin.checker.receiver.implementations

import com.dickow.chortlin.checker.receiver.ChortlinResultCallback
import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

class DefaultChortlinResultCallback : ChortlinResultCallback {
    override fun error(invocationDTO: InvocationDTO) {
        throw ChortlinRuntimeException("Error occurred when checking invocation trace of $invocationDTO")
    }

    override fun error(returnDTO: ReturnDTO) {
        throw ChortlinRuntimeException("Error occurred when checking return trace of $returnDTO")
    }
}