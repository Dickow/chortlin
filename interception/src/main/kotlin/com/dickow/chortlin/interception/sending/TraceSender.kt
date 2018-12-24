package com.dickow.chortlin.interception.sending

import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

interface TraceSender {
    fun send(invocationDTO: DtoDefinitions.InvocationDTO)
    fun send(returnDTO: DtoDefinitions.ReturnDTO)
}