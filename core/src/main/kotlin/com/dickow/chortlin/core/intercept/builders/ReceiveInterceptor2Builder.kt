package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor2

class ReceiveInterceptor2Builder<TClass, T1, T2, R, TMapped>
constructor(private val endpoint: (TClass, T1, T2) -> R, private val mapper: (T1, T2) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor2(endpoint, mapper, processor)
    }
}