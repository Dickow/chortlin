package com.dickow.chortlin.checker.choreography.participant

object ParticipantFactory {

    @JvmStatic
    fun participant(classCanonicalName: String): InternalParticipant {
        return InternalParticipant(classCanonicalName)
    }

    @JvmStatic
    fun participant(clazz: Class<*>) : InternalParticipant {
        return InternalParticipant(clazz.canonicalName)
    }

    @JvmStatic
    fun external(identifier: String): ExternalParticipant {
        return ExternalParticipant(identifier)
    }
}