package com.dickow.chortlin.core.choreography.participant.observation

import java.lang.reflect.Method

abstract class Observable(val clazz: Class<*>, val method: Method) {
    override fun equals(other: Any?): Boolean {
        return if (other is Observable) {
            this.clazz == other.clazz && this.method == other.method
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}