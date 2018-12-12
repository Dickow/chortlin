package com.dickow.chortlin.checker.deserialisation

import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Invocation
import com.dickow.chortlin.shared.trace.Return
import com.dickow.chortlin.shared.trace.dto.InvocationDTO
import com.dickow.chortlin.shared.trace.dto.ReturnDTO
import com.google.gson.Gson

class TraceDeserialiser {
    private val gson = Gson()

    fun deserialise(invocationDTO: InvocationDTO) : Invocation{
        val clazz = Class.forName(invocationDTO.classCanonicalName)
        if(clazz != null){
            val observed = ObservableFactory.observed(clazz, invocationDTO.methodName)
            val originalArguments = invocationDTO.arguments.map { argDTO -> gson.fromJson(argDTO.value, Class.forName(argDTO.argumentClassCanonicalName)) }
            return Invocation(observed, originalArguments.toTypedArray())
        }
        else{
            throw ChortlinRuntimeException("Unable to find class ${invocationDTO.classCanonicalName}")
        }
    }

    fun deserialise(returnDTO: ReturnDTO) : Return {
        val clazz = Class.forName(returnDTO.classCanonicalName)
        if(clazz != null){
            val observed = ObservableFactory.observed(clazz, returnDTO.methodName)
            val originalArguments = returnDTO.arguments.map { argDTO -> gson.fromJson(argDTO.value, Class.forName(argDTO.argumentClassCanonicalName)) }
            val originalReturn = retrieveReturnValue(returnDTO)
            return Return(observed, originalArguments.toTypedArray(), originalReturn)
        }
        else{
            throw ChortlinRuntimeException("Unable to find class ${returnDTO.classCanonicalName}")
        }
    }

    private fun retrieveReturnValue(returnDTO: ReturnDTO): Any? {
        return if (returnDTO.returnValue.argumentClassCanonicalName == Void.TYPE.canonicalName) {
            null
        } else {
            gson.fromJson<Any?>(returnDTO.returnValue.value, Class.forName(returnDTO.returnValue.argumentClassCanonicalName))
        }
    }
}