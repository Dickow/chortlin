package com.dickow.chortlin.core.choreography.participant

import com.dickow.chortlin.core.choreography.method.MethodFactory

class InternalParticipant<C>(val clazz: Class<C>) : Participant {

    fun onMethod(methodName: String) = MethodFactory.method(this, methodName)
    fun <R> onMethod(name: String, signature: (C) -> R) = MethodFactory.method(this, name, signature)
    fun <T1, R> onMethod(name: String, signature: (C, T1) -> R) = MethodFactory.method(this, name, signature)
    fun <T1, T2, R> onMethod(name: String, signature: (C, T1, T2) -> R) = MethodFactory.method(this, name, signature)
    fun <T1, T2, T3, R> onMethod(name: String, signature: (C, T1, T2, T3) -> R) = MethodFactory.method(this, name, signature)
    fun <T1, T2, T3, T4, R> onMethod(name: String, signature: (C, T1, T2, T3, T4) -> R) = MethodFactory.method(this, name, signature)

    override fun equals(other: Any?): Boolean {
        return if (other is InternalParticipant<*>) {
            this.clazz == other.clazz
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return clazz.hashCode()
    }
}