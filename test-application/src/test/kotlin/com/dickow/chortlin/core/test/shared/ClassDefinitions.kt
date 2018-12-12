package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class A {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun receive(): String {
        return "Hello world!"
    }

    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun b(value: String): String {
        return value
    }
}

class B {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun b(value: String): String {
        return value
    }
}
