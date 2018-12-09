package com.dickow.chortlin.shared.trace.dto

import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Invocation

class InvocationDTO constructor() {
    constructor(invocation: Invocation) : this(){
        classCanonicalName = invocation.getObservation().clazz.canonicalName
        methodName = invocation.getObservation().method.name
        arguments = invocation.getArguments()
    }

    private lateinit var classCanonicalName : String
    private lateinit var methodName: String
    private lateinit var arguments : Array<Any?>

    fun toInvocation() : Invocation {
        val clazz = Class.forName(classCanonicalName)
        if(clazz != null){
            val observed = ObservableFactory.observed(clazz, methodName)
            return Invocation(observed, arguments)
        }
        else{
            throw ChortlinRuntimeException("Unable to find class $classCanonicalName")
        }

    }
}