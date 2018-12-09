package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class OnlineFirstClass {
    @ChortlinOnInvoke
    fun method1() {}
    @ChortlinOnInvoke
    fun method2() {}
}

class OnlineSecondClass {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun method1() {}
    @ChortlinOnInvoke
    fun method2() {}
}

class OnlineThirdClass {
    @ChortlinOnInvoke
    fun method1() {}
    @ChortlinOnInvoke
    fun method2() {}
}