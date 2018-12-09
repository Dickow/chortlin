package com.dickow.chortlin.core

import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import com.google.gson.Gson

class JsonInterceptor : InterceptStrategy {
    private val receiver = JsonReceiver()
    private val gson = Gson()
    override fun intercept(trace: TraceElement) {
        when(trace){
            is Invocation -> {
                val json = gson.toJson(InvocationDTO(trace))
                receiver.receiveInvocation(json)
            }
            is Return -> {
                val json = gson.toJson(ReturnDTO(trace))
                receiver.receiveReturn(json)
            }
        }
    }
}