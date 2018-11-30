package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.observation.ObservedParticipant

data class Invocation(private val participant: ObservedParticipant, private val allArguments: Array<Any>) : TraceElement() {
    override fun getArguments(): Array<Any> {
        return allArguments
    }

    override fun getParticipant(): ObservedParticipant {
        return participant
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Invocation) return false

        if (participant != other.participant) return false
        if (!allArguments.contentEquals(other.allArguments)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participant.hashCode()
        result = 31 * result + allArguments.contentHashCode()
        return result
    }
}