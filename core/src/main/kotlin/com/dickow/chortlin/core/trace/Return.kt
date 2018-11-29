package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant

data class Return(private val participant: ObservableParticipant<*>, private val allArguments: Array<Any>, val returnValue: Any) : TraceElement() {
    override fun getArguments(): Array<Any> {
        return allArguments
    }

    override fun getParticipant(): ObservableParticipant<*> {
        return participant
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Return) return false

        if (participant != other.participant) return false
        if (!allArguments.contentEquals(other.allArguments)) return false
        if (returnValue != other.returnValue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = participant.hashCode()
        result = 31 * result + allArguments.contentHashCode()
        result = 31 * result + returnValue.hashCode()
        return result
    }
}