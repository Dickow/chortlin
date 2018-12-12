package com.dickow.chortlin.checker.receiver.implementations

import com.dickow.chortlin.checker.checker.OnlineChecker
import com.dickow.chortlin.checker.checker.result.CheckResult
import com.dickow.chortlin.checker.deserialisation.TraceDeserializer
import com.dickow.chortlin.checker.receiver.ChortlinReceiver
import com.dickow.chortlin.checker.receiver.ChortlinResultCallback
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AsynchronousReceiver(
        private val onlineChecker: OnlineChecker, private val errorCallback: ChortlinResultCallback) : ChortlinReceiver
{
    private val traceDeserializer = TraceDeserializer()

    override fun receive(invocationDTO: InvocationDTO) {
        GlobalScope.launch {
            val trace = traceDeserializer.deserialize(invocationDTO)
            val result = onlineChecker.check(trace)
            when(result){
                CheckResult.None -> errorCallback.error(invocationDTO)
                else -> {}
            }
        }
    }

    override fun receive(returnDTO: ReturnDTO) {
        GlobalScope.launch {
            val trace = traceDeserializer.deserialize(returnDTO)
            val result = onlineChecker.check(trace)
            when(result){
                CheckResult.None -> errorCallback.error(returnDTO)
                else -> {}
            }
        }
    }
}