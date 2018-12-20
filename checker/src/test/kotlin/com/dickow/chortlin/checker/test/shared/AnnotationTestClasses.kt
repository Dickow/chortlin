package com.dickow.chortlin.checker.test.shared

import com.dickow.chortlin.shared.annotations.TraceInvocation
import com.dickow.chortlin.shared.annotations.TraceReturn

class Annotated1 {
    @TraceInvocation
    @TraceReturn
    fun annotatedMethod(input: String): String {
        return input
    }

    fun nonAnnotatedMethod(input: String): String {
        return input
    }
}

class Annotated2 {
    @TraceInvocation
    fun invocationAnnotatedMethod(input: String): String {
        return input
    }

    @TraceReturn
    fun returnAnnotatedMethod(input: String): String {
        return input
    }
}