package com.dickow.chortlin.core.correlation

class ReturnTypesFunction(private val func: (Any) -> Any) : CorrelationFunction() {
    override fun apply(args: Array<Any>): Any {
        return func(args[0])
    }
}