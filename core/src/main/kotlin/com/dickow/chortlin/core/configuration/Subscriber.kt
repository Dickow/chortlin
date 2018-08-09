package com.dickow.chortlin.core.configuration

import com.dickow.chortlin.core.configuration.interaction.Interaction
import com.dickow.chortlin.core.configuration.trigger.Trigger

interface Subscriber {
    fun onTriggerCompleted(trigger: Trigger)
    fun <TMsg> onInteractionCompleted(interaction: Interaction<TMsg>)
}