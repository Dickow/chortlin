package com.dickow.chortlin.interception.strategy

import com.dickow.chortlin.interception.serializer.TraceDTOSerializer
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.TraceElement

class ChortlinIntercept(private val sender: ChortlinSender) : InterceptStrategy {
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