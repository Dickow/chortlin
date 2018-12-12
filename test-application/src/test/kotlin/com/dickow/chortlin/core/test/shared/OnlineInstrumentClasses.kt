package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class OnlineInstrumentFirstClass {
    @ChortlinOnInvoke
    fun method1() {
    }

    @ChortlinOnInvoke
    fun method2() {
    }
}

class OnlineInstrumentSecondClass {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun method1() {
    }

    @ChortlinOnInvoke
    fun method2() {
    }
}

class OnlineInstrumentThirdClass {
    @ChortlinOnInvoke
    fun method1() {
    }

    @ChortlinOnInvoke
    fun method2() {
    }
}