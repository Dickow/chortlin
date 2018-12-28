package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn

class OnlineInstrumentFirstClass {
    @TraceInvocation
    fun method1() {
    }

    @TraceInvocation
    fun method2() {
    }
}

class OnlineInstrumentSecondClass {
    @TraceInvocation
    @TraceReturn
    fun method1() {
    }

    @TraceInvocation
    fun method2() {
    }
}

class OnlineInstrumentThirdClass {
    @TraceInvocation
    fun method1() {
    }

    @TraceInvocation
    fun method2() {
    }
}