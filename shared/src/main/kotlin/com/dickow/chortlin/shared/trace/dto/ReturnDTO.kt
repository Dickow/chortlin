package com.dickow.chortlin.shared.trace.dto

import com.dickow.chortlin.shared.exceptions.ChortlinRuntimeException
import com.dickow.chortlin.shared.observation.ObservableFactory
import com.dickow.chortlin.shared.trace.Return

class ReturnDTO constructor() {
    constructor(returnTrace: Return) : this(){
        classCanonicalName = returnTrace.getObservation().clazz.canonicalName
        methodName = returnTrace.getObservation().method.name
        arguments = returnTrace.getArguments()
        returnValue = returnTrace.returnValue
    }

    private lateinit var classCanonicalName : String
    private lateinit var methodName: String
    private lateinit var arguments : Array<Any?>
    private var returnValue : Any? = null

    fun toReturn() : Return {
        val clazz = Class.forName(classCanonicalName)
        if(clazz != null){
            val observed = ObservableFactory.observed(clazz, methodName)
            return Return(observed, arguments, returnValue)
        }
        else{
            throw ChortlinRuntimeException("Unable to find class $classCanonicalName")
        }

    }
}