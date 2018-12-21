package com.dickow.chortlin.interception.defaults

import com.dickow.chortlin.interception.InterceptStrategy
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.interception.serializer.TraceDTOSerializer
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.TraceElement

class DefaultIntercept(private val sender: TraceSender) : InterceptStrategy {
    private val serializer =  TraceDTOSerializer()
    override fun intercept(trace: TraceElement) {
        when(trace){
            is Invocation -> {
                val dto = serializer.serialize(trace)
                sender.send(dto)
            }
            is Return -> {
                val dto = serializer.serialize(trace)
                sender.send(dto)
            }
        }
    }
}