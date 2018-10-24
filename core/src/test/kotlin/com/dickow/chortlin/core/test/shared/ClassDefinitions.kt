package com.dickow.chortlin.core.test.shared

class A {
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
