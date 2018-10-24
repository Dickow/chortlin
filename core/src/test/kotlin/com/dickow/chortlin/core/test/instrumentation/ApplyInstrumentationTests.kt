package com.dickow.chortlin.core.test.instrumentation

import com.dickow.chortlin.core.choreography.Choreography
import com.dickow.chortlin.core.choreography.participant.ParticipantFactory.participant
import com.dickow.chortlin.core.instrumentation.ASTInstrumentation
import com.dickow.chortlin.core.instrumentation.ByteBuddyInstrumentation
import com.dickow.chortlin.core.instrumentation.strategy.InstrumentationStrategy
import com.dickow.chortlin.core.instrumentation.strategy.StorageStrategy
import com.dickow.chortlin.core.test.shared.Initial
import com.dickow.chortlin.core.test.shared.Second
import com.dickow.chortlin.core.trace.Trace
import com.dickow.chortlin.core.trace.TraceElement
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplyInstrumentationTests {
    private val instrumentationVisitor = ASTInstrumentation(ByteBuddyInstrumentation())
    val traces: MutableList<TraceElement<*>> = LinkedList()
    private val storageStrategy: StorageStrategy = object : StorageStrategy {

        override fun <C> store(trace: TraceElement<C>) {
            traces.add(trace)
        }
    }

    @Test
    fun `apply instrumentation to simple in memory communication`() {
        InstrumentationStrategy.strategy = storageStrategy
        val checker = Choreography.builder()
                .foundMessage(participant(Initial::class.java, "begin"), "start")
                .interaction(participant(Initial::class.java, "delegate"),
                        participant(Second::class.java, "process"), "interaction")
                .end()
                .build()
                .applyInstrumentation(instrumentationVisitor)
                .createChecker()
        Initial().begin()
        assertEquals(3, traces.size)
        assertTrue(checker.check(Trace(traces.toTypedArray())))
    }
}