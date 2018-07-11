package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor1

class ReceiveInterceptor1Builder<TClass, T1, R, TMapped>
constructor(private val endpoint: (TClass, T1) -> R, private val mapper: (T1) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor1(endpoint, mapper, processor)
    }
}