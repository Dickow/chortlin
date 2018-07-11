package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor3

class ReceiveInterceptor3Builder<TClass, T1, T2, T3, R, TMapped>
constructor(private val endpoint: (TClass, T1, T2, T3) -> R, private val mapper: (T1, T2, T3) -> TMapped) : IReceiveInterceptorBuilder<TMapped> {
    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor3(endpoint, mapper, processor)
    }
}