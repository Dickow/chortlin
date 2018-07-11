package com.dickow.chortlin.core.intercept.interceptors

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.jvm.reflect

class ReceiveInterceptor1<TClass, T1, R, TMappedInput, TProcessReturn>
constructor(
        private val endpoint: (TClass, T1) -> R,
        private val mapper: (T1) -> TMappedInput,
        private val processor: (TMappedInput) -> TProcessReturn
) : InvocationHandler, IReceiveInterceptor {
    private val methodName = endpoint.reflect()!!.name

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if (methodName == method!!.name) {
            val arg1 = args?.get(0) as T1
            processor(mapper(arg1))
            return ""
        }

        return method.invoke(proxy, args)
    }
}