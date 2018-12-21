package com.dickow.chortlin.shared.exceptions.factory

import com.dickow.chortlin.shared.exceptions.TypeAPIException
import java.lang.reflect.Method

object TypeApiExceptionFactory {
    fun <T> tooManyMethods(clazz: Class<T>, methodName: String): TypeAPIException {
        return TypeAPIException(
                "class: ${clazz.canonicalName} had more than one method with the name: '$methodName'")
    }

    fun <T> noMethodFound(clazz: Class<T>, methodName: String): TypeAPIException {
        return TypeAPIException(
                "class: ${clazz.canonicalName} had no method with the name: '$methodName'")
    }
}