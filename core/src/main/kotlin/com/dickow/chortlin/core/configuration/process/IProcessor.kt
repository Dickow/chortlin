package com.dickow.chortlin.core.configuration.process

interface IProcessor {
    fun process(arg: Any?): Any?
}