package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.checker.receiver.ChortlinResultCallback
import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

class TestErrorCallback : ChortlinResultCallback {
    override fun error(invocationDTO: InvocationDTO) {
        throw ChortlinRuntimeException("Error encountered for invocation $invocationDTO")
    }

    override fun error(returnDTO: ReturnDTO) {
        throw ChortlinRuntimeException("Error encountered for trace $returnDTO")
    }
}