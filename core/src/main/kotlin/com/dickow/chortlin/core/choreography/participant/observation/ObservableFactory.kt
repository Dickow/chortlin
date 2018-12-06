package com.dickow.chortlin.core.choreography.participant.observation

import com.dickow.chortlin.core.choreography.method.ChortlinMethod
import com.dickow.chortlin.core.choreography.participant.InternalParticipant
import com.dickow.chortlin.core.exceptions.factory.TypeApiExceptionFactory

object ObservableFactory {

    @JvmStatic
    fun observed(clazz: Class<*>, method: String): Observation {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }

        return Observation(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun observable(participant: InternalParticipant<*>, method: ChortlinMethod<*>): ObservableParticipant {
        return ObservableParticipant(participant.clazz, method.jvmMethod)
    }
}