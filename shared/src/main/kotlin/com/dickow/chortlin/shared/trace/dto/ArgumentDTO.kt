package com.dickow.chortlin.shared.trace.dto

class ArgumentDTO constructor() {
    constructor(value: String?, argumentClass: String) : this(){
        this.value = value
        this.argumentClassCanonicalName = argumentClass
    }

    var value: String? = null
    lateinit var argumentClassCanonicalName: String
}