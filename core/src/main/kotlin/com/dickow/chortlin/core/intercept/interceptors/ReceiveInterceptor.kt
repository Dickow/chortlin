package com.dickow.chortlin.core.intercept.interceptors

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.jvm.reflect

class ReceiveInterceptor<TClass, R, TMappedInput, TProcessReturn>
(
        private val endpoint: (TClass) -> R,
        private val mapper: () -> TMappedInput,
        private val processor: (TMappedInput) -> TProcessReturn
) : InvocationHandler, IReceiveInterceptor {
    private val methodName: String = this.endpoint.reflect()!!.name

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if (method!!.name == methodName) {
            processor(mapper())
            return ""
        }

        return method.invoke(proxy, args)
    }
}