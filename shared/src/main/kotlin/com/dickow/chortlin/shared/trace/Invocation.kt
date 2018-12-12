package com.dickow.chortlin.shared.trace

import com.dickow.chortlin.shared.observation.Observation


data class Invocation(private val observation: Observation, private val allArguments: Array<Any?>) : TraceElement() {
    override fun getArguments(): Array<Any?> {
        return allArguments
    }

    override fun getObservation(): Observation {
        return observation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Invocation) return false

        if (observation != other.observation) return false
        if (!allArguments.contentEquals(other.allArguments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = observation.hashCode()
        result = 31 * result + allArguments.contentHashCode()
        return result
    }
}