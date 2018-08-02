package com.dickow.chortlin.core.configuration.process

interface IProcessor {
    fun process(args: Array<Any>): Any
}