package com.dickow.chortlin.shared.observation

import com.dickow.chortlin.shared.exceptions.factory.TypeApiExceptionFactory

object ObservableFactory {

    @JvmStatic
    fun observed(clazz: Class<*>, method: String): Observation {
        val concreteMethods = clazz.methods.filter { m -> m.name == method }
        if (concreteMethods.size > 1) {
            throw TypeApiExceptionFactory.tooManyMethods(clazz, method)
        }
        if (concreteMethods.isEmpty()) {
            throw TypeApiExceptionFactory.noMethodFound(clazz, method)
        }

        return Observation(clazz, concreteMethods[0])
    }
}