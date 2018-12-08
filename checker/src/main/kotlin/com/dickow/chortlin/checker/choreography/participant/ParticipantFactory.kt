package com.dickow.chortlin.checker.choreography.participant

@Suppress("UNUSED_PARAMETER")
object ParticipantFactory {

    @JvmStatic
    fun <C> participant(clazz: Class<C>): InternalParticipant<C> {
        return InternalParticipant(clazz)
    }

    @JvmStatic
    fun external(identifier: String): ExternalParticipant {
        return ExternalParticipant(identifier)
    }
}