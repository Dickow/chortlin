package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import com.dickow.chortlin.shared.exceptions.factory.TypeApiExceptionFactory
import java.lang.reflect.Method

object MethodFactory {
    fun <C> method(participant: InternalParticipant<C>, methodName: String): ObservableMethod {
        val method = getMethodFromName(participant.clazz, methodName)
        return TypelessMethod(method, participant)
    }

    private fun <C> getMethodFromName(clazz: Class<C>, method: String): Method {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }
        return concreteMethods[0]
    }
}