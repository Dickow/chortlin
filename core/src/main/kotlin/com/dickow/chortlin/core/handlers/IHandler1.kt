package com.dickow.chortlin.core.handlers

interface IHandler1<T1, TMapped, R> {
    fun mapInput(arg: T1): TMapped
    fun process(input: TMapped): R
}