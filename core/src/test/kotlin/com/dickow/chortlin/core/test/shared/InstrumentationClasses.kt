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
