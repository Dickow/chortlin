package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.observation.Observation

data class Return(private val observation: Observation, private val allArguments: Array<Any>, val returnValue: Any?) : TraceElement() {
    override fun getArguments(): Array<Any> {
        return allArguments
    }

    override fun getObservation(): Observation {
        return observation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Return) return false

        if (observation != other.observation) return false
        if (!allArguments.contentEquals(other.allArguments)) return false
        if (returnValue != other.returnValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = observation.hashCode()
        result = 31 * result + allArguments.contentHashCode()
        result = 31 * result + returnValue.hashCode()
        return result
    }
}