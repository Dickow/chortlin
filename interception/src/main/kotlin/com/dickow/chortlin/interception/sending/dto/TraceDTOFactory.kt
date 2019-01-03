package com.dickow.chortlin.interception.sending.dto

import com.dickow.chortlin.interception.annotations.Named
import com.dickow.chortlin.interception.observation.Observation
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions
import com.google.protobuf.*
import java.lang.reflect.Field
import java.lang.reflect.Method

class TraceDTOFactory {

    fun buildInvocationDTO(observed: Observation, arguments: Array<out Any?>): DtoDefinitions.InvocationDTO {
        val builder = DtoDefinitions.InvocationDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method

        builder.observed = DtoDefinitions.ObservedDTO.newBuilder().setParticipant(classCanonicalName).setMethod(methodName).build()
        val mappedArguments = arguments.mapIndexed{index, argument -> buildArgument(resolveArgumentName(index, observed.jvmMethod), argument) }
        builder.addAllArguments(mappedArguments)
        return builder.build()
    }

    fun buildReturnDTO(observed: Observation, arguments: Array<out Any?>, returnValue: Any?): DtoDefinitions.ReturnDTO {
        val builder = DtoDefinitions.ReturnDTO.newBuilder()
        val classCanonicalName = observed.clazz.canonicalName
        val methodName = observed.method

        builder.observed = DtoDefinitions.ObservedDTO.newBuilder().setParticipant(classCanonicalName).setMethod(methodName).build()
        val mappedArguments = arguments.mapIndexed{index, argument -> buildArgument(resolveArgumentName(index, observed.jvmMethod), argument) }
        builder.addAllArguments(mappedArguments)
        builder.returnValue = buildValue(returnValue).build()
        return builder.build()
    }

    private fun buildArgument(argumentName: String, argument: Any?): DtoDefinitions.ArgumentDTO? {
        val argumentBuilder = DtoDefinitions.ArgumentDTO.newBuilder().setIdentifier(argumentName)
        argumentBuilder.value = buildValue(argument).build()
        return argumentBuilder.build()
    }

    private fun buildValue(argument: Any?): Value.Builder {
        return when(argument){
            null -> Value.newBuilder().setNullValue(NullValue.NULL_VALUE)
            is Number -> Value.newBuilder().setNumberValue(argument.toDouble())
            is String -> Value.newBuilder().setStringValue(argument)
            is Collection<*> -> Value.newBuilder().setListValue(
                    ListValue.newBuilder().addAllValues(argument.map { listItem -> buildValue(listItem).build() }))
            is Array<*> -> Value.newBuilder().setListValue(
                    ListValue.newBuilder().addAllValues(argument.map { arrayItem -> buildValue(arrayItem).build() }))
            is Boolean -> Value.newBuilder().setBoolValue(argument)
            is Enum<*> -> Value.newBuilder().setStringValue(argument.name)
            else -> {
                Value.newBuilder()
                        .setStructValue(Struct.newBuilder().putAllFields(
                                argument.javaClass.declaredFields.map { field ->
                                    Pair(field.name, buildValue(getFieldValue(argument, field)).build()) }.toMap()
                        ))
            }
        }
    }

    private fun getFieldValue(instance: Any?, field: Field) : Any?{
        field.isAccessible = true
        val retrievedValue = field.get(instance)
        field.isAccessible = false
        return retrievedValue
    }

    private fun resolveArgumentName(index: Int, method: Method): String {
        val param = method.parameters[index]
        return when {
            param.isNamePresent -> param.name
            param.isAnnotationPresent(Named::class.java) -> {
                val nameAnnotation = param.getDeclaredAnnotation(Named::class.java)
                nameAnnotation.name
            }
            else -> param.name
        }
    }
}