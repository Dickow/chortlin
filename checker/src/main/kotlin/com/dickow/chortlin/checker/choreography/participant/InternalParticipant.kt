package com.dickow.chortlin.checker.choreography.participant

import com.dickow.chortlin.checker.choreography.method.MethodFactory

class InternalParticipant(val identifier: String) : Participant {

    fun onMethod(methodName: String) = MethodFactory.method(this, methodName)

    override fun equals(other: Any?): Boolean {
        return if (other is InternalParticipant) {
            this.identifier == other.identifier
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }
}