package com.dickow.chortlin.core.util

import java.lang.reflect.Method

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

    fun getMethodMatchForInputTypes(expectedTypes: Array<Class<*>>, methods: Array<Method>): Method? {
        return methods.firstOrNull { m -> TypeUtil.typesMatch(expectedTypes, m.parameterTypes) }
    }

    fun getMethodMatchForReturnType(expectedReturnType: Class<*>, methods: Array<Method>): Method? {
        return methods.firstOrNull { m -> m.parameterTypes.size == 1 && expectedReturnType == m.parameterTypes[0] }
    }

}