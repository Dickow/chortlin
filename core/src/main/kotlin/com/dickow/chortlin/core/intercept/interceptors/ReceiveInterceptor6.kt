package com.dickow.chortlin.core.intercept.interceptors

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.jvm.reflect

class ReceiveInterceptor6<TClass, T1, T2, T3, T4, T5, T6, R, TMappedInput, TProcessReturn> constructor(
        private val endpoint: (TClass, T1, T2, T3, T4, T5, T6) -> R,
        private val mapper: (T1, T2, T3, T4, T5, T6) -> TMappedInput,
        private val processor: (TMappedInput) -> TProcessReturn
) : InvocationHandler, IReceiveInterceptor {
    private val methodName = this.endpoint.reflect()!!.name

    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        if (methodName == method!!.name) {
            val arg1 = args?.get(0) as T1
            val arg2 = args[1] as T2
            val arg3 = args[2] as T3
            val arg4 = args[3] as T4
            val arg5 = args[4] as T5
            val arg6 = args[5] as T6
            processor(mapper(arg1, arg2, arg3, arg4, arg5, arg6))
            return ""
        }

        return method.invoke(proxy, args)
    }
}