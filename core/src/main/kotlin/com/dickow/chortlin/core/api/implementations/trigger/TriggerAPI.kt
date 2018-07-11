package com.dickow.chortlin.core.api.implementations.trigger

import com.dickow.chortlin.core.api.interfaces.trigger.ITriggerAPI
import com.dickow.chortlin.core.interaction.Interaction
import com.dickow.chortlin.core.trigger.Trigger

class TriggerAPI : ITriggerAPI {
    override fun thenInteractWith(interaction: Interaction): Trigger {
        return Trigger()
    }

    override fun thenInteractWith(interactions: Collection<Interaction>): Trigger {
        return Trigger()
    }

    override fun end(): Trigger {
        return Trigger()
    }
}