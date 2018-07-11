package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor4

class ReceiveInterceptor4Builder<TClass, T1, T2, T3, T4, R, TMapped>
constructor(private val endpoint: (TClass, T1, T2, T3, T4) -> R, private val mapper: (T1, T2, T3, T4) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor4(endpoint, mapper, processor)
    }
}