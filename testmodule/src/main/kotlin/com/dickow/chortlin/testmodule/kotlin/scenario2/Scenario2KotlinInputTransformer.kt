package com.dickow.chortlin.testmodule.kotlin.scenario2

import com.dickow.chortlin.core.continuation.Transform

class Scenario2KotlinInputTransformer : Transform<Scenario2User, Int> {
    override fun transform(value: Scenario2User): Int {
        return value.id.toInt()
    }
}