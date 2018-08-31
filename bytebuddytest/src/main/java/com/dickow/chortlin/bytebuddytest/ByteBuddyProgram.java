package com.dickow.chortlin.bytebuddytest;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.Arrays;

public class ByteBuddyProgram {
    public static void main(String[] args) {
        ByteBuddyAgent.install();
        new AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named(JavaIntercepted.class.getName()))
                .transform((builder, typeDescription, classLoader, javaModule) ->
                        builder.visit(Advice.to(MethodAdvisor.class).on(ElementMatchers.named("doSomeMagic"))))
                .installOnByteBuddyAgent();
        String methodName = MethodUtil.Companion.getMethodName(
                Interception2.class, Interception2::doSomething, String[].class, String.class);
        new AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(ElementMatchers.named(JavaInterception2.class.getName()))
                .transform((builder, typeDescription, classLoader, javaModule) ->
                        builder.visit(Advice.to(AroundAdvisor.class).on(ElementMatchers.named(methodName)))
                )
                .installOnByteBuddyAgent();

        new JavaIntercepted().doSomeMagic("Hello", 42);
        new JavaInterception2().doSomething(new String[]{"Hello", "World", "!"});
    }
}

class JavaIntercepted {
    Object[] doSomeMagic(String arg1, Integer arg2) {
        System.out.println("doing something");
        return new Object[]{arg1, arg2};
    }
}

class JavaInterception2 {
    String doSomething(String[] arg1) {
        Arrays.stream(arg1).forEach(System.out::println);
        return String.join("", arg1);
    }
}
