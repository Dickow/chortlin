package com.dickow.chortlin.testmodule.kotlin.scenario3

import com.dickow.chortlin.core.handlers.IHandler1
import com.dickow.chortlin.core.handlers.IHandler2
import com.dickow.chortlin.core.message.Message

class Scenario3KotlinTriggerHandler : IHandler2<Int, String, Int, Int> {
    override fun process(input: Int): Int {
        return input * 200
    }

    override fun mapInput(arg1: Int, arg2: String): Int {
        return arg1 + arg2.toInt()
    }
}

class Scenario3KotlinInteractionHandler : IHandler1<Message<Int>, Int, Boolean> {
    override fun mapInput(arg: Message<Int>): Int {
        return arg.payload!!
    }

    override fun process(input: Int): Boolean {
        return input > 0
    }
}