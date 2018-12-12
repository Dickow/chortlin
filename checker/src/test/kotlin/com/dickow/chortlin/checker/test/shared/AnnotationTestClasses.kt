package com.dickow.chortlin.checker.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class Annotated1 {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun annotatedMethod(input: String): String {
        return input
    }

    fun nonAnnotatedMethod(input: String): String {
        return input
    }
}

class Annotated2 {
    @ChortlinOnInvoke
    fun invocationAnnotatedMethod(input: String): String {
        return input
    }

    @ChortlinOnReturn
    fun returnAnnotatedMethod(input: String): String {
        return input
    }
}