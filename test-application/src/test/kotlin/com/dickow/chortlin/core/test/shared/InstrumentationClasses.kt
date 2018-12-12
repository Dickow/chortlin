package com.dickow.chortlin.core.test.shared

import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn

class Initial {
    @ChortlinOnInvoke
    fun begin() {
        delegate()
    }

    @ChortlinOnInvoke
    fun delegate() {
        Second().process()
    }
}

class Second {
    @ChortlinOnInvoke
    fun process() {
    }
}


class FirstClass {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun first() {
        SecondClass().second()
    }
}

class SecondClass {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun second() {
        ThirdClass().third()
        return
    }
}

class ThirdClass {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun third() {
        return
    }
}


class PartialFirst {
    @ChortlinOnInvoke
    fun first() {
        second()
    }

    @ChortlinOnInvoke
    fun second() {
        PartialSecond().second()
    }
}

class PartialSecond {
    @ChortlinOnInvoke
    fun second() {}

    @ChortlinOnInvoke
    fun third() {}
}

class PartialThird {
    @ChortlinOnInvoke
    @ChortlinOnReturn
    fun third() {}
}
