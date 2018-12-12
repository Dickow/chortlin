package com.dickow.chortlin.shared.trace.dto

class InvocationDTO constructor() {

    constructor(classCanonicalName: String, methodName: String, arguments: List<ArgumentDTO>) : this() {
        this.classCanonicalName = classCanonicalName
        this.methodName = methodName
        this.arguments = arguments
    }

    lateinit var classCanonicalName : String
    lateinit var methodName: String
    lateinit var arguments : List<ArgumentDTO>
}