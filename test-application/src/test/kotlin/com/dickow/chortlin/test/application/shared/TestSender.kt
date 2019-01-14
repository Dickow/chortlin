package com.dickow.chortlin.test.application.shared

import com.dickow.chortlin.checker.checker.ChoreographyChecker
import com.dickow.chortlin.interception.sending.TraceSender
import com.dickow.chortlin.shared.trace.protobuf.DtoDefinitions

class TestSender(private val checker: ChoreographyChecker) : TraceSender {
    var sendInvocationCallback: (DtoDefinitions.InvocationDTO) -> Unit = {}
    var sendReturnCallback: (DtoDefinitions.ReturnDTO) -> Unit = {}

    override fun send(invocationDTO: DtoDefinitions.InvocationDTO) {
        sendInvocationCallback(invocationDTO)
        checker.check(invocationDTO)
    }

    override fun send(returnDTO: DtoDefinitions.ReturnDTO) {
        sendReturnCallback(returnDTO)
        checker.check(returnDTO)
    }

}