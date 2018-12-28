package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.interception.annotations.TraceInvocation
import com.dickow.chortlin.interception.annotations.TraceReturn


class Initial {
    @TraceInvocation
    fun begin() {
        delegate()
    }

    @TraceInvocation
    fun delegate() {
        Second().process()
    }
}

class Second {
    @TraceInvocation
    fun process() {
    }
}


class FirstClass {
    @TraceInvocation
    @TraceReturn
    fun first() {
        SecondClass().second()
    }
}

class SecondClass {
    @TraceInvocation
    @TraceReturn
    fun second() {
        ThirdClass().third()
        return
    }
}

class ThirdClass {
    @TraceInvocation
    @TraceReturn
    fun third() {
        return
    }
}


class PartialFirst {
    @TraceInvocation
    fun first() {
        second()
    }

    @TraceInvocation
    fun second() {
        PartialSecond().second()
    }
}

class PartialSecond {
    @TraceInvocation
    fun second() {}

    @TraceInvocation
    fun third() {}
}

class PartialThird {
    @TraceInvocation
    @TraceReturn
    fun third() {}
}
