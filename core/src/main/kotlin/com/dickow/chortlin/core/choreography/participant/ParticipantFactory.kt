package com.dickow.chortlin.core.choreography.participant

import com.dickow.chortlin.core.choreography.participant.entity.ExternalEntity
import com.dickow.chortlin.core.exceptions.factory.TypeApiExceptionFactory
import java.lang.reflect.Method


@Suppress("UNUSED_PARAMETER")
object ParticipantFactory {

    @JvmStatic
    fun <C, R> participant(clazz: Class<C>, method: String, signature: (C) -> R): Participant0<R> {
        val concreteMethods = getMethodFromName(clazz, method)
        return Participant0(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <C, T1, R> participant(clazz: Class<C>, method: String, signature: (C, T1) -> R): Participant1<T1, R> {
        val concreteMethods = getMethodFromName(clazz, method)
        return Participant1(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <C, T1, T2, R> participant(clazz: Class<C>, method: String, signature: (C, T1, T2) -> R): Participant2<T1, T2, R> {
        val concreteMethods = getMethodFromName(clazz, method)
        return Participant2(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <C, T1, T2, T3, R> participant(clazz: Class<C>, method: String, signature: (C, T1, T2, T3) -> R): Participant3<T1, T2, T3, R> {
        val concreteMethods = getMethodFromName(clazz, method)
        return Participant3(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <C, T1, T2, T3, T4, R> participant(clazz: Class<C>, method: String, signature: (C, T1, T2, T3, T4) -> R): Participant4<T1, T2, T3, T4, R> {
        val concreteMethods = getMethodFromName(clazz, method)
        return Participant4(clazz, concreteMethods[0])
    }

    private fun <C> getMethodFromName(clazz: Class<C>, method: String): List<Method> {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }
        return concreteMethods
    }

    @JvmStatic
    fun external(identifier: String): NonObservableParticipant {
        return NonObservableParticipant(ExternalEntity(identifier))
    }
}