package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn

class A {
    @TraceInvocation
    @TraceReturn
    fun receive(): String {
        return "Hello world!"
    }

    @TraceInvocation
    @TraceReturn
    fun b(value: String): String {
        return value
    }
}

class B {
    @TraceInvocation
    @TraceReturn
    fun b(value: String): String {
        return value
    }
}
