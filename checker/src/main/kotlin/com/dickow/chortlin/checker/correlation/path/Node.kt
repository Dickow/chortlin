package com.dickow.chortlin.checker.correlation.path

import com.dickow.chortlin.checker.trace.value.*
import com.dickow.chortlin.shared.constants.StringConstants
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException

class Node(val key: String) : Path {
    var next: Node? = null

    override fun apply(input: Value): Value {
        return when (input) {
            is RootValue -> applyToRoot(input)
            is ObjectValue -> applyToObject(input)
            else -> {
                throw ChoreographyRuntimeException("Error applying specified correlation value path ${System.lineSeparator()}" +
                        "Could not apply the key '$key' to the value: $input")
            }
        }
    }

    private fun applyToRoot(input: RootValue) : Value {
        return if(key == StringConstants.ROOT){
            if(next != null){
               next!!.apply(input.value)
            }
            else{
                input.value
            }
        }
        else {
            throw ChoreographyRuntimeException("Root value encountered but current path does not specify to pick root.")
        }
    }

    private fun applyToObject(input: ObjectValue) : Value {
        return if(input.values[key] != null){
            if(next!= null){
                next!!.apply(input.values[key]!!)
            }
            else{
                input.values[key]!!
            }
        }
        else{
            throw ChoreographyRuntimeException("Error applying specified correlation value path ${System.lineSeparator()}" +
                    "Could not apply the key $key to the value: $input")
        }
    }

    override fun toString(): String {
        return if(next != null){
            "$key -> ${next.toString()}"
        }
        else{
            key
        }
    }
}