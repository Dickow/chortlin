package com.dickow.chortlin.core.choreography.participant

import com.dickow.chortlin.core.choreography.participant.entity.ExternalEntity
import com.dickow.chortlin.core.exceptions.factory.TypeApiExceptionFactory
import com.dickow.chortlin.core.util.TypeUtil


object ParticipantFactory {
    @JvmStatic
    fun <T> participant(clazz: Class<T>, method: String): ObservableParticipant<T> {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }

        return ObservableParticipant(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <T> participant(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): ObservableParticipant<T> {
        val concreteMethods = clazz.methods
                .filter { m -> m.returnType == returnType && TypeUtil.typesMatch(m.parameterTypes, paramTypes) }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, returnType, concreteMethods, paramTypes)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, returnType, paramTypes)
        }

        return ObservableParticipant(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun <T> participant(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): ObservableParticipant<T> {
        try {
            val concreteMethod = clazz.getDeclaredMethod(methodName, *paramTypes)
            return ObservableParticipant(clazz, concreteMethod)
        } catch (e: Exception) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, methodName)
        }
    }

    @JvmStatic
    fun external(identifier: String): NonObservableParticipant {
        return NonObservableParticipant(ExternalEntity(identifier))
    }
}