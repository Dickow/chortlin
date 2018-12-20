package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import com.dickow.chortlin.shared.exceptions.factory.TypeApiExceptionFactory
import java.lang.reflect.Method

object MethodFactory {
    fun <C> method(participant: InternalParticipant<C>, methodName: String): ObservableMethod<*> {
        val method = getMethodFromName(participant.clazz, methodName)
        return TypelessMethod(method, participant)
    }

    @JvmStatic
    fun <C, R> method(participant: InternalParticipant<C>, name: String, signature: (C) -> R): Method0<C, R> {
        val method = getMethodFromName(participant.clazz, name)
        return Method0(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1) -> R): Method1<C, T1, R> {
        val method = getMethodFromName(participant.clazz, name)
        return Method1(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2) -> R): Method2<C, T1, T2, R> {
        val method = getMethodFromName(participant.clazz, name)
        return Method2(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, T3, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2, T3) -> R): Method3<C, T1, T2, T3, R> {
        val method = getMethodFromName(participant.clazz, name)
        return Method3(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, T3, T4, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2, T3, T4) -> R): Method4<C, T1, T2, T3, T4, R> {
        val method = getMethodFromName(participant.clazz, name)
        return Method4(method, signature, participant)
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