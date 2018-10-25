package com.dickow.chortlin.core.trace

import com.dickow.chortlin.core.choreography.participant.Participant

data class Return<C>(val participant: Participant<C>) : TraceElement<C>(participant)