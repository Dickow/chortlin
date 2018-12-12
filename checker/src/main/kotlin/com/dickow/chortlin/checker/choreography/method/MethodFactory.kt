package com.dickow.chortlin.checker.choreography.method

import com.dickow.chortlin.checker.choreography.participant.InternalParticipant
import com.dickow.chortlin.shared.exceptions.factory.TypeApiExceptionFactory
import java.lang.reflect.Method

object MethodFactory {
    fun <C> method(participant: InternalParticipant<C>, methodName: String): ChortlinMethod<*> {
        val method = getMethodFromName(participant.clazz, methodName)
        return NoSigChortlinMethod(method, participant)
    }

    @JvmStatic
    fun <C, R> method(participant: InternalParticipant<C>, name: String, signature: (C) -> R): ChortlinMethod0<C, R> {
        val method = getMethodFromName(participant.clazz, name)
        return ChortlinMethod0(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1) -> R): ChortlinMethod1<C, T1, R> {
        val method = getMethodFromName(participant.clazz, name)
        return ChortlinMethod1(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2) -> R): ChortlinMethod2<C, T1, T2, R> {
        val method = getMethodFromName(participant.clazz, name)
        return ChortlinMethod2(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, T3, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2, T3) -> R): ChortlinMethod3<C, T1, T2, T3, R> {
        val method = getMethodFromName(participant.clazz, name)
        return ChortlinMethod3(method, signature, participant)
    }

    @JvmStatic
    fun <C, T1, T2, T3, T4, R> method(participant: InternalParticipant<C>, name: String, signature: (C, T1, T2, T3, T4) -> R): ChortlinMethod4<C, T1, T2, T3, T4, R> {
        val method = getMethodFromName(participant.clazz, name)
        return ChortlinMethod4(method, signature, participant)
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