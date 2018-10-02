package com.dickow.chortlin.core.api.exceptions

import com.dickow.chortlin.core.types.path.Path
import java.lang.reflect.Method
import java.util.function.Function

object TypeApiExceptionFactory {
    fun <T> tooManyMethods(clazz: Class<T>, methodName: String): TypeAPIException {
        return TypeAPIException(
                "class: ${clazz.canonicalName} had more than one method with the name: '$methodName'")
    }

    fun <T> noMethodFound(clazz: Class<T>, methodName: String): TypeAPIException {
        return TypeAPIException(
                "class: ${clazz.canonicalName} had no method with the name: '$methodName'")
    }

    fun <T> tooManyMethods(clazz: Class<T>, returnType: Class<*>, concreteMethods: List<Method>, paramTypes: Array<out Class<*>>): TypeAPIException {
        val paramString = paramTypes.joinToString(", ") { m -> m.name }
        val matchingMethods = concreteMethods.joinToString(System.lineSeparator(), transform = { m -> m.name })
        val message =
                "class: ${clazz.canonicalName} " +
                        "had more than one matching method: ${System.lineSeparator()} [$matchingMethods] " +
                        "for the signature: ($paramString) -> ${returnType.name}"
        return TypeAPIException(message)
    }

    fun <T> noMethodFound(clazz: Class<T>, returnType: Class<*>, paramTypes: Array<out Class<*>>): TypeAPIException {
        val paramString = paramTypes.joinToString(", ") { m -> m.name }
        val message = "class: ${clazz.canonicalName} had no matching method for the signature: ($paramString) -> ${returnType.name}"
        return TypeAPIException(message)
    }

    fun invalidForkConfiguration(forkedPaths: Array<out Function<Path, Path>>): TypeAPIException {
        val message = "Invalid fork configuration encountered, " +
                "expected more than one path but found ${forkedPaths.size} paths"
        return TypeAPIException(message)
    }

    fun invalidChoiceConfiguration(choicedPaths: Array<out Function<Path, Path>>): TypeAPIException {
        val message = "Invalid choice configuration encountered, " +
                "expected more than one path but found ${choicedPaths.size} paths"
        return TypeAPIException(message)
    }
}