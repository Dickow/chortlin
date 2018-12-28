package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn

class OnlineFirstClass {
    @TraceInvocation
    @TraceReturn
    fun method1() {}
    @TraceInvocation
    @TraceReturn
    fun method2() {}
}

class OnlineSecondClass {
    @TraceInvocation
    @TraceReturn
    fun method1() {}
    @TraceInvocation
    @TraceReturn
    fun method2() {}
}

class OnlineThirdClass {
    @TraceInvocation
    @TraceReturn
    fun method1() {}
    @TraceInvocation
    @TraceReturn
    fun method2() {}
}