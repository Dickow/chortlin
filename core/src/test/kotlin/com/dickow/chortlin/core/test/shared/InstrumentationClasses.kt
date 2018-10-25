package com.dickow.chortlin.core.test.shared

class Initial {
    fun begin() {
        delegate()
    }

    fun delegate() {
        Second().process()
    }
}

class Second {
    fun process() {
    }
}


class FirstClass {
    fun first() {
        SecondClass().second()
    }
}

class SecondClass {
    fun second() {
        ThirdClass().third()
        return
    }
}

class ThirdClass {
    fun third() {
        return
    }
}
