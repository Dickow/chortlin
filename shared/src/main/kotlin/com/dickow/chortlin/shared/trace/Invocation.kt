package com.dickow.chortlin.shared.trace

import com.dickow.chortlin.shared.observation.Observation


data class Invocation(private val observation: Observation, private val argumentTree: Map<*, *>) : TraceElement() {
    override fun getArgumentTree(): Map<*, *> {
        return argumentTree
    }

    override fun getObservation(): Observation {
        return observation
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Invocation) return false

        if (observation != other.observation) return false

        return true
    }

    override fun hashCode(): Int {
        return observation.hashCode()
    }
}