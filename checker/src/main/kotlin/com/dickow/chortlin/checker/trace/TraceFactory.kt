package com.dickow.chortlin.checker.trace

import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.google.gson.Gson

class TraceFactory {
    private val gson = Gson()

    fun buildInvocation(invocationDTO: DtoDefinitions.InvocationDTO): Invocation {
        val observed = ObservableParticipant(invocationDTO.observed.participant, invocationDTO.observed.method)
        val argumentTree = gson.fromJson(invocationDTO.argumentTree, Map::class.java)
        return Invocation(observed, argumentTree)
    }

    fun buildReturn(returnDTO: DtoDefinitions.ReturnDTO): Return {
        val observed = ObservableParticipant(returnDTO.observed.participant, returnDTO.observed.method)
        val argumentTree = gson.fromJson(returnDTO.argumentTree, Map::class.java)
        val returnValue = gson.fromJson(returnDTO.returnValue, Map::class.java)
        return Return(observed, argumentTree, returnValue)
    }
}