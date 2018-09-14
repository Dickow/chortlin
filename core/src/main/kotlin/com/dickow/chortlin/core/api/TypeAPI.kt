package com.dickow.chortlin.core.api

import com.dickow.chortlin.core.api.exceptions.TypeApiExceptionFactory
import com.dickow.chortlin.core.types.Participant

class TypeAPI {
    fun <T> participant(clazz: Class<T>, method: String): Participant<T> {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }

        return Participant(clazz, concreteMethods[0])
    }

    fun <T> participant(clazz: Class<T>, returnType: Class<*>, vararg paramTypes: Class<*>): Participant<T> {
        val concreteMethods = clazz.methods
                .filter { m -> m.returnType == returnType && typesMatch(m.parameterTypes, paramTypes) }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, returnType, concreteMethods, paramTypes)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, returnType, paramTypes)
        }

        return Participant(clazz, concreteMethods[0])
    }

    fun <T> participant(clazz: Class<T>, methodName: String, vararg paramTypes: Class<*>): Participant<T> {
        try {
            val concreteMethod = clazz.getDeclaredMethod(methodName, *paramTypes)
            return Participant(clazz, concreteMethod)
        } catch (e: Exception) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, methodName)
        }
    }

    private fun typesMatch(types1: Array<Class<*>>, types2: Array<out Class<*>>): Boolean {
        if (types1.size != types2.size) {
            return false
        }

        for (i in 0 until types1.size) {
            if (types1[i] != types2[i]) {
                return false
            }
        }

        return true
    }
}