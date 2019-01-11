package com.dickow.chortlin.checker.correlation.path

import com.dickow.chortlin.checker.trace.value.ObjectValue
import com.dickow.chortlin.checker.trace.value.Value
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException

open class Node(val key: String) : Path {
    var next: Node? = null

    override fun apply(input: Value): Value {
        return when (input) {
            is ObjectValue -> applyToObject(input)
            else -> {
                throw ChoreographyRuntimeException("Error applying specified correlation value path ${System.lineSeparator()}" +
                        "Could not apply the key '$key' to the value: $input")
            }
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