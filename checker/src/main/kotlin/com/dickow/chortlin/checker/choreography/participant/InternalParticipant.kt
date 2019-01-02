package com.dickow.chortlin.checker.choreography.participant

class InternalParticipant(val identifier: String) : Participant {

    fun onMethod(methodName: String) = ObservableParticipant(this.identifier, methodName)

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