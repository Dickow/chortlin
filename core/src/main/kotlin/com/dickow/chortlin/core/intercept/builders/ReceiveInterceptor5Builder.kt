package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor5

class ReceiveInterceptor5Builder<TClass, T1, T2, T3, T4, T5, R, TMapped>
constructor(private val endpoint: (TClass, T1, T2, T3, T4, T5) -> R, private val mapper: (T1, T2, T3, T4, T5) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor5(endpoint, mapper, processor)
    }
}