package com.dickow.chortlin.checker.correlation.path

import com.dickow.chortlin.checker.trace.value.Value

interface Path {
    fun apply(input: Value): Value
}