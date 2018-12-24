package com.dickow.chortlin.checker.choreography.participant

import com.dickow.chortlin.checker.choreography.method.MethodFactory

class InternalParticipant<C>(val clazz: Class<C>) : Participant {

    fun onMethod(methodName: String) = MethodFactory.method(this, methodName)

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