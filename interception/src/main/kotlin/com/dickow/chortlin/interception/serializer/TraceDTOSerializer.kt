package com.dickow.chortlin.interception.serializer

import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.dto.ArgumentDTO
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import com.google.gson.Gson

class TraceDTOSerializer {
    private val gson = Gson()

    fun serialize(invocationTrace: Invocation) : InvocationDTO{
        val classCanonicalName = invocationTrace.getObservation().clazz.canonicalName
        val methodName = invocationTrace.getObservation().method.name
        val arguments = invocationTrace.getArguments().map { arg -> serializeArgument(arg) }
        return InvocationDTO(classCanonicalName, methodName, arguments)
    }

    fun serialize(returnTrace: Return) : ReturnDTO {
        val classCanonicalName = returnTrace.getObservation().clazz.canonicalName
        val methodName = returnTrace.getObservation().method.name
        val arguments = returnTrace.getArguments().map { arg -> serializeArgument(arg) }
        val returnValue = serializeArgument(returnTrace.returnValue)
        return ReturnDTO(classCanonicalName, methodName, arguments, returnValue)
    }

    private fun serializeArgument(argument: Any?) : ArgumentDTO {
        return if(argument != null){
            val argumentClassCanonicalName = argument.javaClass.canonicalName
            val value = gson.toJson(argument)
            ArgumentDTO(value, argumentClassCanonicalName)
        }
        else{
            ArgumentDTO(null, Void.TYPE.canonicalName)
        }
    }
}