package com.dickow.chortlin.core.instrumentation

import com.dickow.chortlin.core.choreography.participant.Participant
import com.dickow.chortlin.core.instrumentation.advice.AfterAdvisor
import com.dickow.chortlin.core.instrumentation.advice.AroundAdvisor
import com.dickow.chortlin.core.instrumentation.advice.BeforeAdvisor
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.matcher.ElementMatchers

class ByteBuddyInstrumentation : Instrumentation {
    init {
        ByteBuddyAgent.install()
    }

    override fun <C> around(participant: Participant<C>) {
        applyAdvice(participant, AroundAdvisor::class.java)
    }

    override fun <C> before(participant: Participant<C>) {
        applyAdvice(participant, BeforeAdvisor::class.java)
    }

    override fun <C> after(participant: Participant<C>) {
        applyAdvice(participant, AfterAdvisor::class.java)
    }

    private fun <C> applyAdvice(participant: Participant<C>, adviceClass: Class<*>) {
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