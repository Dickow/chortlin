package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.instrumentation.advice.AfterAdvisorNoReturn
import com.dickow.chortlin.core.instrumentation.advice.AfterAdvisorWithReturn
import com.dickow.chortlin.core.instrumentation.advice.BeforeAdvisor
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.matcher.ElementMatchers

/**
 * Static instance of the ByteBuddyInstrumentation class,
 * it is important that this class is static to maintain knowledge of the instrumented classes.
 */
object ByteBuddyInstrumentation : Instrumentation {
    private val appliedBeforeAdvices: MutableSet<Participant> = HashSet()
    private val appliedAfterAdvices: MutableSet<Participant> = HashSet()

    init {
        ByteBuddyAgent.install()
    }

    override fun before(participant: Participant) {
        if (!appliedBeforeAdvices.contains(participant)) {
            applyAdvice(participant, BeforeAdvisor::class.java)
            appliedBeforeAdvices.add(participant)
        }
    }

    override fun after(participant: Participant) {
        if (!appliedAfterAdvices.contains(participant)) {
            if (participant.method.returnType == Void.TYPE) {
                applyAdvice(participant, AfterAdvisorNoReturn::class.java)
            } else {
                applyAdvice(participant, AfterAdvisorWithReturn::class.java)
            }

            appliedAfterAdvices.add(participant)
        }
    }

    private fun applyAdvice(participant: Participant, adviceClass: Class<*>) {
        AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named(participant.clazz.name))
                .transform { builder, _, _, _ ->
                    builder.visit(Advice.to(adviceClass).on(ElementMatchers.named(participant.method.name)))
                }
                .installOnByteBuddyAgent()
    }
}