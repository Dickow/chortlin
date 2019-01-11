package com.dickow.chortlin.checker.correlation.path

import com.dickow.chortlin.checker.trace.value.Value
import com.dickow.chortlin.shared.constants.StringConstants

class Root : Node(StringConstants.ROOT) {

    override fun apply(input: Value): Value {
        return input
    }
}