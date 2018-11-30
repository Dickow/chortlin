package com.dickow.chortlin.core.choreography.participant.observation

import com.dickow.chortlin.core.exceptions.factory.TypeApiExceptionFactory
import com.dickow.chortlin.core.util.TypeUtil

object ObservedParticipantFactory {

    @JvmStatic
    fun observed(clazz: Class<*>, method: String): ObservedParticipant {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }

        return ObservedParticipant(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun observed(clazz: Class<*>, returnType: Class<*>, vararg paramTypes: Class<*>): ObservedParticipant {
        val concreteMethods = clazz.methods
                .filter { m -> m.returnType == returnType && TypeUtil.typesMatch(m.parameterTypes, paramTypes) }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, returnType, concreteMethods, paramTypes)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, returnType, paramTypes)
        }

        return ObservedParticipant(clazz, concreteMethods[0])
    }

    @JvmStatic
    fun observed(clazz: Class<*>, methodName: String, vararg paramTypes: Class<*>): ObservedParticipant {
        try {
            val concreteMethod = clazz.getDeclaredMethod(methodName, *paramTypes)
            return ObservedParticipant(clazz, concreteMethod)
        } catch (e: Exception) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, methodName)
        }
    }
}