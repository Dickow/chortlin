package com.dickow.chortlin.core.util

object TypeUtil {
    fun typesMatch(types1: Array<Class<*>>, types2: Array<out Class<*>>): Boolean {
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