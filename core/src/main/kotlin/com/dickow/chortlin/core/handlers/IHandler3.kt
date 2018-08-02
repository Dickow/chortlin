package com.dickow.chortlin.core.handlers

interface IHandler3<T1, T2, T3, TMapped, R> {
    fun mapInput(arg1: T1, arg2: T2, arg3: T3): TMapped
    fun process(input: TMapped): R
}