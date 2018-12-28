package com.dickow.chortlin.interception.dto

import com.dickow.chortlin.interception.observation.Observation
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.google.gson.Gson
import java.lang.reflect.Method

class TraceDTOFactory {
    private val gson = Gson()

    fun buildInvocationDTO(observed: Observation, arguments: Array<out Any?>): DtoDefinitions.InvocationDTO {
        val builder = DtoDefinitions.InvocationDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method

        builder.observed = builder.observedBuilder.setParticipant(classCanonicalName).setMethod(methodName).build()
        val argumentTree = mapOf(Pair("root", arguments.mapIndexed { index, argument ->
            Pair(getArgumentName(index, observed.jvmMethod), argument)
        }.toMap()))
        builder.argumentTree = gson.toJson(argumentTree)
        return builder.build()
    }

    fun buildReturnDTO(observed: Observation, arguments: Array<out Any?>, returnValue: Any?): DtoDefinitions.ReturnDTO {
        val builder = DtoDefinitions.ReturnDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method
        builder.observed = builder.observedBuilder.setParticipant(classCanonicalName).setMethod(methodName).build()
        val argumentTree = mapOf(Pair("root", arguments.mapIndexed { index, argument ->
            Pair(getArgumentName(index, observed.jvmMethod), argument)
        }.toMap()))
        builder.argumentTree = gson.toJson(argumentTree)
        builder.returnValue = gson.toJson(mapOf(Pair("root", gson.toJson(returnValue))))
        return builder.build()
    }

    private fun getArgumentName(index: Int, method: Method): String {
        return if (method.parameters.any()) {
            if (method.parameters[0].isNamePresent) {
                method.parameters[index].name
            } else {
                index.toString()
            }
        } else {
            index.toString()
        }
    }
}