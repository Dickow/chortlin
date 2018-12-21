package com.dickow.chortlin.shared.observation

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
        var result = method.hashCode()
        result = 31 * result + method.hashCode()
        return result
    }
}