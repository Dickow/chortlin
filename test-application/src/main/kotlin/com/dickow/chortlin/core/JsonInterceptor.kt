package com.dickow.chortlin.core

import com.dickow.chortlin.interception.instrumentation.strategy.InterceptStrategy
import com.dickow.chortlin.shared.trace.TraceElement
import com.dickow.chortlin.shared.trace.dto.TraceElementDTO
import com.google.gson.Gson

class JsonInterceptor : InterceptStrategy {
    private val receiver = JsonReceiver()
    private val gson = Gson()
    override fun intercept(trace: TraceElement) {
        val json = gson.toJson(TraceElementDTO(trace))
        receiver.receive(json)
    }
}