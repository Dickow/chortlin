package com.dickow.chortlin.interception.instrumentation

import com.dickow.chortlin.interception.instrumentation.advice.AfterAdvisorNoReturn
import com.dickow.chortlin.interception.instrumentation.advice.AfterAdvisorWithReturn
import com.dickow.chortlin.interception.instrumentation.advice.BeforeAdvisor
import com.dickow.chortlin.shared.annotations.ChortlinOnInvoke
import com.dickow.chortlin.shared.annotations.ChortlinOnReturn
import com.dickow.chortlin.shared.observation.ObservableParticipant
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.description.type.TypeDescription
import net.bytebuddy.matcher.ElementMatchers

/**
 * Static instance of the ByteBuddyInstrumentation class,
 * it is important that this class is static to maintain knowledge of the instrumented classes.
 */
object ByteBuddyInstrumentation : Instrumentation {
    private val appliedBeforeAdvices: MutableSet<ObservableParticipant> = HashSet()
    private val appliedAfterAdvices: MutableSet<ObservableParticipant> = HashSet()

    init {
        ByteBuddyAgent.install()
    }

    override fun before(participant: ObservableParticipant) {
        if (!appliedBeforeAdvices.contains(participant)) {
            applyAdvice(participant, BeforeAdvisor::class.java)
            appliedBeforeAdvices.add(participant)
        }
    }

    override fun after(participant: ObservableParticipant) {
        if (!appliedAfterAdvices.contains(participant)) {
            if (participant.method.returnType == Void.TYPE) {
                applyAdvice(participant, AfterAdvisorNoReturn::class.java)
            } else {
                applyAdvice(participant, AfterAdvisorWithReturn::class.java)
            }

            appliedAfterAdvices.add(participant)
        }
    }

    @JvmStatic
    fun instrumentAnnotatedMethods(){
        AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.declaresMethod {
                    methodDescription -> methodDescription.declaredAnnotations.any {
                    annotation -> annotation.annotationType.isAssignableTo(ChortlinOnInvoke::class.java) } })
                .transform { builder, _, _, _ ->
                    builder.visit(Advice.to(BeforeAdvisor::class.java)
                            .on(ElementMatchers.isAnnotatedWith(ChortlinOnInvoke::class.java)))
                }
                .installOnByteBuddyAgent()

        AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.declaresMethod {
                    methodDescription -> methodDescription.declaredAnnotations.any {
                    annotation -> annotation.annotationType.isAssignableTo(ChortlinOnReturn::class.java) } })
                .transform { builder, _, _, _ ->
                    builder.visit(Advice.to(AfterAdvisorWithReturn::class.java)
                            .on(ElementMatchers.isAnnotatedWith(ChortlinOnReturn::class.java)))
                }
                .installOnByteBuddyAgent()
    }

    private fun applyAdvice(participant: ObservableParticipant, adviceClass: Class<*>) {
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