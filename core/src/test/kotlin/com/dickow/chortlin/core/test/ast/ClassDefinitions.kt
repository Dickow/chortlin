package com.dickow.chortlin.core.test.ast

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