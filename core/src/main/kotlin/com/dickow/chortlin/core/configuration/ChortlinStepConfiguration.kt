package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.lookup.Lookup
import com.dickow.chortlin.core.configuration.trigger.Trigger

class ChortlinStepConfiguration constructor(private val lookup: Lookup) : Subscriber {
    override fun onTriggerCompleted(trigger: Trigger) {
        this.lookup.add(trigger)
    }

    override fun <TMsg> onInteractionCompleted(interaction: Interaction<TMsg>) {
    }
}