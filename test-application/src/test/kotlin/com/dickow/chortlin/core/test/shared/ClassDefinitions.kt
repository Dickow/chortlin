package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke

class A {
    @ChortlinOnInvoke
    fun receive(): String {
        return "Hello world!"
    }

    fun b(value: String): String {
        return value
    }
}

class B {
    fun b(value: String): String {
        return value
    }
}
