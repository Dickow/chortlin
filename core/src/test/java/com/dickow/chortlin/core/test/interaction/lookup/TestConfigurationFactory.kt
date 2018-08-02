package com.dickow.chortlin.core.test.interaction.lookup

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.interaction.InteractionBuilder
import com.dickow.chortlin.core.configuration.map.Mapper1
import com.dickow.chortlin.core.configuration.process.Processor1
import com.dickow.chortlin.core.configuration.trigger.Trigger
import com.dickow.chortlin.core.configuration.trigger.TriggerBuilder

class TestConfigurationFactory {
    fun createInteraction(endpoint: Any): Interaction {
        val builder = InteractionBuilder()
        builder.endpoint = endpoint
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        return builder.noChannel()
    }

    fun createTrigger(endpoint: Any): Trigger {
        val builder = TriggerBuilder()
        builder.endpoint = endpoint
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        return builder.noChannel()
    }

    fun createTrigger(endpoint: Any, interactions: Collection<Interaction>): Trigger {
        val builder = TriggerBuilder()
        builder.endpoint = endpoint
        builder.mapper = Mapper1 { s: String -> s }
        builder.processor = Processor1 { s: String -> s }
        builder.interactions = interactions
        return builder.noChannel()
    }
}