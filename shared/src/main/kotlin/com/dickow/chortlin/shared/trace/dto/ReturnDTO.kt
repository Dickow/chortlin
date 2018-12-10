package com.dickow.chortlin.shared.trace.dto

class ReturnDTO constructor() {
    constructor(classCanonicalName: String, methodName: String, arguments: List<ArgumentDTO>, returnValue: ArgumentDTO) : this(){
        this.classCanonicalName = classCanonicalName
        this.methodName = methodName
        this.arguments = arguments
        this.returnValue = returnValue
    }

    lateinit var classCanonicalName : String
    lateinit var methodName: String
    lateinit var arguments : List<ArgumentDTO>
    lateinit var returnValue : ArgumentDTO
}