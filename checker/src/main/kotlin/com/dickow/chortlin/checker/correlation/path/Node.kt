package com.dickow.chortlin.checker.correlation.path

import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException

open class Node(val key: String) : Path {
    var next: Node? = null

    override fun apply(input: Any?): Any {
        return when (input) {
            is Map<*, *> -> {
                when (next) {
                    null ->
                        if (input.containsKey(key)) {
                            input[key]!!
                        } else {
                            throw ChoreographyRuntimeException("Unable to find key: $key in input $input")
                        }
                    else -> {
                        if (input.containsKey(key)) {
                            this.next!!.apply(input[key]!!)
                        } else {
                            throw ChoreographyRuntimeException("Unable to find key: $key in input $input")
                        }
                    }
                }
            }
            else -> {
                throw ChoreographyRuntimeException("Error applying specified correlation value path ${System.lineSeparator()}" +
                        "Could not apply the key $key to the value: $input")
            }
        }
    }
}