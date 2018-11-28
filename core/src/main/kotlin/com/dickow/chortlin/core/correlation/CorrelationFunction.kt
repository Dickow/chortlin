package com.dickow.chortlin.core.correlation

abstract class CorrelationFunction {
    abstract fun apply(args: Array<Any>): Any
}