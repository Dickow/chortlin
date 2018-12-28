package com.dickow.chortlin.checker.trace

import com.dickow.chortlin.checker.choreography.participant.ObservableParticipant
import com.dickow.chortlin.checker.trace.value.*
import com.dickow.chortlin.shared.exceptions.ChoreographyRuntimeException
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

class TraceFactory {

    fun buildInvocation(invocationDTO: DtoDefinitions.InvocationDTO): Invocation {
        val observed = ObservableParticipant(invocationDTO.observed.participant, invocationDTO.observed.method)
        val root = RootValue(ObjectValue(invocationDTO.argumentsList.map { arg -> Pair(arg.identifier, toValue(arg.value)) }.toMap()))
        return Invocation(observed, root)
    }

    private fun toValue(value: com.google.protobuf.Value): Value {
        return when(value.kindCase){
            com.google.protobuf.Value.KindCase.NULL_VALUE -> NullValue()
            com.google.protobuf.Value.KindCase.NUMBER_VALUE -> NumberValue(value.numberValue)
            com.google.protobuf.Value.KindCase.STRING_VALUE -> StringValue(value.stringValue)
            com.google.protobuf.Value.KindCase.BOOL_VALUE -> BooleanValue(value.boolValue)
            com.google.protobuf.Value.KindCase.STRUCT_VALUE ->
                ObjectValue(value.structValue.fieldsMap.mapValues { entry -> toValue(entry.value) })
            com.google.protobuf.Value.KindCase.LIST_VALUE -> ListValue(value.listValue.valuesList.map { toValue(it) })
            com.google.protobuf.Value.KindCase.KIND_NOT_SET -> NullValue()
            else -> throw ChoreographyRuntimeException("Error occurred when trying to parse protobuffer value: $value")
        }
    }

    fun buildReturn(returnDTO: DtoDefinitions.ReturnDTO): Return {
        val observed = ObservableParticipant(returnDTO.observed.participant, returnDTO.observed.method)
        val root = RootValue(ObjectValue(returnDTO.argumentsList.map { arg -> Pair(arg.identifier, toValue(arg.value)) }.toMap()))
        val returnValue = RootValue(toValue(returnDTO.returnValue))
        return Return(observed, root, returnValue)
    }
}