package com.dickow.chortlin.shared.transformation

import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.observation.Observable
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.google.gson.Gson

class TraceBuilder {
    private val gson = Gson()

    fun buildInvocationDTO(observed: Observable, arguments: Array<out Any?>): DtoDefinitions.InvocationDTO {
        val builder = DtoDefinitions.InvocationDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method.name

        builder.observed = builder.observedBuilder.setParticipant(classCanonicalName).setMethod(methodName).build()
        val argumentTree = mapOf(Pair("root", arguments.mapIndexed { index, any -> Pair(index, any) }.toMap()))
        builder.argumentTree = gson.toJson(argumentTree)
        return builder.build()
    }

    fun buildReturnDTO(observed: Observable, arguments: Array<out Any?>, returnValue: Any?): DtoDefinitions.ReturnDTO {
        val builder = DtoDefinitions.ReturnDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method.name
        builder.observed = builder.observedBuilder.setParticipant(classCanonicalName).setMethod(methodName).build()
        val argumentTree = mapOf(Pair("root", arguments.mapIndexed { index, any -> Pair(index, any) }.toMap()))
        builder.argumentTree = gson.toJson(argumentTree)
        builder.returnValue = gson.toJson(mapOf(Pair("root", gson.toJson(returnValue))))
        return builder.build()
    }

    fun buildInvocation(invocationDTO: DtoDefinitions.InvocationDTO): Invocation {
        val clazz = Class.forName(invocationDTO.observed.participant)
        if (clazz != null) {
            val observed = ObservableFactory.observed(clazz, invocationDTO.observed.method)
            val argumentTree = gson.fromJson(invocationDTO.argumentTree, Map::class.java)
            return Invocation(observed, argumentTree)
        } else {
            throw ChoreographyRuntimeException("Unable to find participant ${invocationDTO.observed.participant}")
        }
    }

    fun buildReturn(returnDTO: DtoDefinitions.ReturnDTO): Return {
        val clazz = Class.forName(returnDTO.observed.participant)
        if (clazz != null) {
            val observed = ObservableFactory.observed(clazz, returnDTO.observed.method)
            val argumentTree = gson.fromJson(returnDTO.argumentTree, Map::class.java)
            val returnValue = gson.fromJson(returnDTO.returnValue, Map::class.java)
            return Return(observed, argumentTree, returnValue)
        } else {
            throw ChoreographyRuntimeException("Unable to find class ${returnDTO.observed.participant}")
        }
    }
}