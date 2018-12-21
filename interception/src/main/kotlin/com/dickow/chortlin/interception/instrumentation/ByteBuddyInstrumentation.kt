package com.dickow.chortlin.interception.instrumentation

import com.dickow.chortlin.interception.instrumentation.advice.AfterAdvisor
import com.dickow.chortlin.interception.instrumentation.advice.BeforeAdvisor
import com.dickow.chortlin.shared.annotations.TraceInvocation
import com.dickow.chortlin.shared.annotations.TraceReturn
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.agent.builder.AgentBuilder
import net.bytebuddy.asm.Advice
import net.bytebuddy.matcher.ElementMatchers

/**
 * Static instance of the ByteBuddyInstrumentation class,
 * it is important that this class is static to maintain knowledge of the instrumented classes.
 */
object ByteBuddyInstrumentation{
    @JvmStatic
    private var hasInstrumented = false
    init {
        ByteBuddyAgent.install()
    }

    @JvmStatic
    fun instrumentAnnotatedMethods(){
        synchronized(this) {
            if(hasInstrumented){return}

            AgentBuilder.Default()
                    .disableClassFormatChanges()
                    .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                    .type(ElementMatchers.declaresMethod {
                        methodDescription -> methodDescription.declaredAnnotations.any {
                        annotation -> annotation.annotationType.isAssignableTo(TraceInvocation::class.java) } })
                    .transform { builder, _, _, _ ->
                        builder.visit(Advice.to(BeforeAdvisor::class.java)
                                .on(ElementMatchers.isAnnotatedWith(TraceInvocation::class.java)))
                    }
                    .installOnByteBuddyAgent()

            AgentBuilder.Default()
                    .disableClassFormatChanges()
                    .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                    .type(ElementMatchers.declaresMethod {
                        methodDescription -> methodDescription.declaredAnnotations.any {
                        annotation -> annotation.annotationType.isAssignableTo(TraceReturn::class.java) } })
                    .transform { builder, _, _, _ ->
                        builder.visit(Advice.to(AfterAdvisor::class.java)
                                .on(ElementMatchers.isAnnotatedWith(TraceReturn::class.java)))
                    }
                    .installOnByteBuddyAgent()
            hasInstrumented = true
        }
    }
}