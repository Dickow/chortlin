package com.dickow.chortlin.core.intercept.builders

interface IReceiveInterceptorBuilder<TMapped> {
    fun <TProcessReturn> setProcessor(processor: (TMapped) -> TProcessReturn)
}