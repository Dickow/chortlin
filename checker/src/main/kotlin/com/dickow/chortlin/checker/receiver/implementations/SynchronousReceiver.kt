package com.dickow.chortlin.checker.receiver.implementations

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.deserialisation.TraceDeserialiser
import com.dickow.chortlin.checker.receiver.ChortlinReceiver
import com.dickow.chortlin.checker.receiver.ChortlinResultCallback
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO

class SynchronousReceiver(
        private val onlineChecker: OnlineChecker, private val errorCallback: ChortlinResultCallback) : ChortlinReceiver {
    private val traceDeserializer = TraceDeserialiser()

    override fun receive(invocationDTO: InvocationDTO) {
        val trace = traceDeserializer.deserialise(invocationDTO)
        val result = onlineChecker.check(trace)
        when(result){
            CheckResult.None -> errorCallback.error(invocationDTO)
            else -> {}
        }
    }

    override fun receive(returnDTO: ReturnDTO) {
        val trace = traceDeserializer.deserialise(returnDTO)
        val result = onlineChecker.check(trace)
        when(result){
            CheckResult.None -> errorCallback.error(returnDTO)
            else -> {}
        }
    }
}