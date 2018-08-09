package com.dickow.chortlin.core.test.interaction.lookup

import com.dickow.chortlin.core.api.endpoint.Endpoint
import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper1
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder
import com.dickow.chortlin.core.continuation.ChortlinContinuation
import com.dickow.chortlin.core.continuation.Transform
import com.dickow.chortlin.core.test.interaction.shared.KotlinSinkChannel

class TestConfigurationFactory {
    fun <TIn, TClass> createInteraction(clazz: Class<TClass>, name: String): Interaction<TIn> {
        val builder = InteractionBuilder()
        builder.endpoint = Endpoint(clazz, name)
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        return builder.build(KotlinSinkChannel())
    }

    fun <TClass> createTrigger(clazz: Class<TClass>, name: String): Trigger {
        val builder = TriggerBuilder()
        builder.endpoint = Endpoint(clazz, name)
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        return builder.build()
    }

    fun <TClass> createTrigger(clazz: Class<TClass>, name: String, interactions: Collection<Interaction<String>>): Trigger {
        val builder = TriggerBuilder()
        builder.endpoint = Endpoint(clazz, name)
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        interactions.forEach {
            val continuation = ChortlinContinuation(StringToStringTransform(), it)
            builder.addContinuation(continuation)
        }
        return builder.build()
    }

    private class StringToStringTransform : Transform<String, String> {
        override fun transform(value: String): String {
            return value
        }
    }
}