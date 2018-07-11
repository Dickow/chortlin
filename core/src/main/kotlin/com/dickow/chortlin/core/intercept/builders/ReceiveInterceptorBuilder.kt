package com.dickow.chortlin.core.intercept.builders

import com.dickow.chortlin.core.intercept.interceptors.ReceiveInterceptor

class ReceiveInterceptorBuilder<TClass, R, TMapped>
constructor(private val method: (TClass) -> R, private val mapper: () -> TMapped) : IReceiveInterceptorBuilder<TMapped> {

    override fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn) {
        ReceiveInterceptor(method, mapper, processor)
    }
}