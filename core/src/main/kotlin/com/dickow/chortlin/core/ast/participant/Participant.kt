package com.dickow.chortlin.core.ast.participant

import java.lang.reflect.Method

data class Participant<T>(val clazz: Class<T>, val method: Method) {
    override fun equals(other: Any?): Boolean {
        return if (other is Participant<*>) {
            clazz.canonicalName === other.clazz.canonicalName && method.name === other.method.name
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = clazz.canonicalName.hashCode()
        result = 31 * result + method.name.hashCode()
        return result
    }
}