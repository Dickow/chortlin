package com.dickow.chortlin.core.intercept.interceptors

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.jvm.reflect

class ReceiveInterceptor3<TClass, T1, T2, T3, R, TMappedInput, TProcessReturn> constructor(
        private val endpoint: (TClass, T1, T2, T3) -> R,
        private val mapper: (T1, T2, T3) -> TMappedInput,
        private val processor: (TMappedInput) -> TProcessReturn
) : InvocationHandler, IReceiveInterceptor {
    private val methodName = this.endpoint.reflect()!!.name

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if (methodName == method!!.name) {
            val arg1 = args?.get(0) as T1
            val arg2 = args[1] as T2
            val arg3 = args[2] as T3
            processor(mapper(arg1, arg2, arg3))
            return ""
        }

        return method.invoke(proxy, args)
    }
}