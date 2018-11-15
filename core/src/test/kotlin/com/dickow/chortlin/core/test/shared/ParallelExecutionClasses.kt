package com.dickow.chortlin.core.test.shared

class ParallelClassA {
    fun method1(value: Int): Boolean {
        return value > 0
    }
}

class ParallelClassB {
    fun method1() {
    }

    fun method2(input: String) {
    }
}

class ParallelClassC {
    fun method1() {
    }

    fun method2(arg: Boolean): String {
        return if (arg) {
            "true"
        } else {
            "false"
        }
    }
}