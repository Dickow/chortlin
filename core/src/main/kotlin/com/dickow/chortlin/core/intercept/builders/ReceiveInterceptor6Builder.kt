package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor6

class ReceiveInterceptor6Builder<TClass, T1, T2, T3, T4, T5, T6, R, TMapped>
constructor(private val endpoint: (TClass, T1, T2, T3, T4, T5, T6) -> R, private val mapper: (T1, T2, T3, T4, T5, T6) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor6(endpoint, mapper, processor)
    }
}