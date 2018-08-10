@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.dickow.chortlin.testmodule.kotlin.scenario2

import com.dickow.chortlin.core.handlers.IHandler1
import com.dickow.chortlin.core.handlers.IHandler3
import com.dickow.chortlin.core.message.Message
import kotlin.test.assertEquals

class Scenario2KotlinTriggerHandler : IHandler3<Long, String, Boolean, Scenario2User, Scenario2User> {
    override fun mapInput(id: Long, name: String, isAdmin: Boolean): Scenario2User {
        return Scenario2User(id, name, isAdmin)
    }

    override fun process(input: Scenario2User): Scenario2User {
        return input
    }
}

class Scenario2KotlinInteractionHandler(private val passedId: Long) : IHandler1<Message<Int>, Long, Boolean> {
    override fun mapInput(id: Message<Int>): Long {
        assertEquals(passedId, id.payload?.toLong())
        return id.payload!!.toLong()
    }

    override fun process(input: Long): Boolean {
        return true
    }
}