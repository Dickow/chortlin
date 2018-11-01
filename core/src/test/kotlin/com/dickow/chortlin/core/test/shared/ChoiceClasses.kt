package com.dickow.chortlin.core.test.shared

class ChoiceClassA {
    fun method1(input: String) {
        if (input.equals("a", true)) {
            ChoiceClassB().method1()
        } else {
            ChoiceClassC().method1()
        }
    }
}

class ChoiceClassB {
    fun method1() {}
}

class ChoiceClassC {
    fun method1() {}
}