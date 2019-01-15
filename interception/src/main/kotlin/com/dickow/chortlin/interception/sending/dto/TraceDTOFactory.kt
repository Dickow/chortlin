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
        builder.returnValue = buildProtobufValue(returnValue).build()
        return builder.build()
    }

    private fun buildArgument(argumentName: String, argument: Any?): DtoDefinitions.ArgumentDTO? {
        val argumentBuilder = DtoDefinitions.ArgumentDTO.newBuilder().setIdentifier(argumentName)
        argumentBuilder.value = buildProtobufValue(argument).build()
        return argumentBuilder.build()
    }

    private fun buildProtobufValue(argument: Any?): Value.Builder {
        return when(argument){
            null -> newValue().setNullValue(NullValue.NULL_VALUE)
            is Number -> newValue().setNumberValue(argument.toDouble())
            is String -> newValue().setStringValue(argument)
            is Collection<*> -> newValue().setListValue(buildListValue(argument))
            is Array<*> -> newValue().setListValue(buildListValue(argument))
            is Boolean -> newValue().setBoolValue(argument)
            is Enum<*> -> newValue().setStringValue(argument.name)
            else -> newValue().setStructValue(buildStructValue(argument))
        }
    }

    private fun buildStructValue(argument: Any) : Struct.Builder {
        return Struct.newBuilder().putAllFields(
                argument.javaClass.declaredFields.map { field ->
                    Pair(field.name, buildProtobufValue(getFieldValue(argument, field)).build()) }.toMap()
        )
    }

    private fun buildListValue(arguments: Array<*>) : ListValue.Builder {
        return ListValue.newBuilder().addAllValues(arguments.map { listItem -> buildProtobufValue(listItem).build() })
    }

    private fun buildListValue(arguments: Collection<*>) : ListValue.Builder {
        return ListValue.newBuilder().addAllValues(arguments.map { listItem -> buildProtobufValue(listItem).build() })
    }

    private fun newValue() : Value.Builder {
        return Value.newBuilder()
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