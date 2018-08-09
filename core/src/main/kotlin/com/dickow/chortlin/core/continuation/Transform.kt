package com.dickow.chortlin.core.continuation

interface Transform<in TIn, out TOut> {
    fun transform(value: TIn): TOut
}