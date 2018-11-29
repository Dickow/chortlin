package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.ObservableParticipant
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
    private val appliedBeforeAdvices: MutableSet<ObservableParticipant<*>> = HashSet()
    private val appliedAfterAdvices: MutableSet<ObservableParticipant<*>> = HashSet()

    init {
        ByteBuddyAgent.install()
    }

    override fun <T> before(participant: ObservableParticipant<T>) {
        if (!appliedBeforeAdvices.contains(participant)) {
            applyAdvice(participant, BeforeAdvisor::class.java)
            appliedBeforeAdvices.add(participant)
        }
    }

    override fun <T> after(participant: ObservableParticipant<T>) {
        if (!appliedAfterAdvices.contains(participant)) {
            if (participant.method.returnType == Void.TYPE) {
                applyAdvice(participant, AfterAdvisorNoReturn::class.java)
            } else {
                applyAdvice(participant, AfterAdvisorWithReturn::class.java)
            }

            appliedAfterAdvices.add(participant)
        }
    }

    private fun <T> applyAdvice(participant: ObservableParticipant<T>, adviceClass: Class<*>) {
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