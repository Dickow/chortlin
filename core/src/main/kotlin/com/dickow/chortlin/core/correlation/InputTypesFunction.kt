package com.dickow.chortlin.core.correlation

class InputTypesFunction(private val func: (Array<Any>) -> Any) : CorrelationFunction() {
    override fun apply(args: Array<Any>): Any {
        return func(args)
    }
}